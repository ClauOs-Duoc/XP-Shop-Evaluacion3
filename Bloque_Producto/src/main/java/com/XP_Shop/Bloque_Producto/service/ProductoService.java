package com.XP_Shop.Bloque_Producto.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Producto.dto.ProductoDTO;
import com.XP_Shop.Bloque_Producto.model.Imagen;
import com.XP_Shop.Bloque_Producto.model.Producto;
import com.XP_Shop.Bloque_Producto.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    private ProductoDTO convertirProductoADTO(Producto producto){
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setDescripcionProducto(producto.getDescripcionProducto());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setIdProductos(producto.getProductos().getIdProductos());
        dto.setIdMarcas(producto.getMarcas().getIdMarcas());
        dto.setIdCategorias(producto.getCategorias().getIdCategorias());
        if (producto.getImagenes() != null){
            List<String> imagenes = new ArrayList<>();
            for(Imagen imagen : producto.getImagenes()){
                imagenes.add(imagen.getNombreImagen());
            }
            dto.setImagenes(imagenes);
        }

        return dto;
    }

    public List<ProductoDTO> listarProducto() {
        log.info("Listando todos los productos");
        return productoRepository.findAll().stream()
                    .map(this::convertirProductoADTO)
                    .toList();
    }
    
    public ProductoDTO buscarProductoPorId(Integer id) {
        log.info("Buscando producto por ID: {}", id);
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertirProductoADTO(producto);
    }

    public ProductoDTO guardarProducto(Producto producto) {
        log.info("Guardando nuevo producto");
        Producto nuevoProducto = productoRepository.save(producto);
        log.info("Producto guardado con ID: {}", nuevoProducto.getIdProducto());
        return convertirProductoADTO(nuevoProducto);
    }

    public ProductoDTO actualizarProducto(Integer id, Producto producto) {
        log.info("Actualizando producto con ID: {}", id);
        Producto productoExistente = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El producto no existe."));
        if (producto.getNombreProducto() != null) {
            productoExistente.setNombreProducto(producto.getNombreProducto());
        }
        if (producto.getDescripcionProducto() != null) {
            productoExistente.setDescripcionProducto(producto.getDescripcionProducto());
        }
        if (producto.getPrecio() != null) {
            productoExistente.setPrecio(producto.getPrecio());
        }
        if (producto.getStock() != null) {
            productoExistente.setStock(producto.getStock());
        }
        if (producto.getMarcas() != null) {
            productoExistente.setMarcas(producto.getMarcas());
        }
        if (producto.getCategorias() != null) {
            productoExistente.setCategorias(producto.getCategorias());
        }
        if (producto.getImagenes() != null) {
            productoExistente.setImagenes(producto.getImagenes());
        }
        if (producto.getProductos() != null) {
            productoExistente.setProductos(producto.getProductos());
        }

        Producto updatedProducto = productoRepository.save(productoExistente);
        log.info("Producto actualizado con ID: {}", id);
        return convertirProductoADTO(updatedProducto);

    }

    public Void eliminarProducto(Integer id) {
        log.info("Eliminando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar el producto con ID " + id + " no existe."));
        productoRepository.delete(producto);
        log.info("Producto con ID {} eliminado exitosamente", id);
        return null;
    }
    
}
