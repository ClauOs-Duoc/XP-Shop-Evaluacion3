package com.XP_Shop.Bloque_Producto.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Producto.model.Productos;
import com.XP_Shop.Bloque_Producto.repository.ProductosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductosService {

    private static final Logger log = LoggerFactory.getLogger(ProductosService.class);

    @Autowired
    private ProductosRepository productosRepository;

    public List<Productos> listaProductos() {
        return productosRepository.findAll();
    }

    public Productos buscarProductosPorId(Integer id) {
        log.info("Buscando productos con ID: {}", id);
        return productosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Los productos no existen."));
    }

    public Productos guardarProductos(Productos productos) {
        log.info("Guardando productos: {}", productos.getProducto());
        Productos savedProductos = productosRepository.save(productos);
        log.info("Productos guardados con ID: {}", savedProductos.getIdProductos());
        return savedProductos;
    }

    public Productos actualizarProductos(Integer id, Productos productos) {
        log.info("Actualizando productos con ID: {}", id);
        Productos productosExistente = productosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Los productos no existen."));

        if (productos.getId_detalleBoletas() != null) {
            productosExistente.setId_detalleBoletas(productos.getId_detalleBoletas());
        }
        if (productos.getProducto() != null) {
            productosExistente.setProducto(productos.getProducto());
        }
        
        log.info("Productos actualizados con ID: {}", id);
        return productosRepository.save(productosExistente);
    }

    public String eliminarProductos(Integer id) {
        log.info("Eliminando productos con ID: {}", id);
        try {
            Productos productos = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar los productos con ID" + id + " no existe."));
            productosRepository.delete(productos);
            log.info("Productos eliminados con ID: {}", id);
            return "Los productos han sido eliminados correctamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
}
