package com.XP_Shop.Bloque_Producto.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Producto.dto.DetalleBoletaExternoDTO;
import com.XP_Shop.Bloque_Producto.model.Productos;
import com.XP_Shop.Bloque_Producto.repository.ProductosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductosService {

    private static final Logger log = LoggerFactory.getLogger(ProductosService.class);

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private ProductosValidations productosValidations;

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
        return productosRepository.save(productos);
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

        return productosRepository.save(productosExistente);
    }

    public String eliminarProductos(Integer id) {
        log.info("Eliminando productos con ID: {}", id);
        try {
            Productos productos = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar, el producto con ID " + id + " no existe."));
            productosRepository.delete(productos);
            return "Los productos han sido eliminados correctamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public DetalleBoletaExternoDTO obtenerDetalleDeProduto(Integer idDetalle) {
        log.info("Obteniendo detalle boleta con ID: {}", idDetalle);
        return productosValidations.obtenerDetalleBoleta(idDetalle);
    }
    
}
