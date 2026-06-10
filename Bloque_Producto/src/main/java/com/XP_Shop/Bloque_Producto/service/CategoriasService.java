package com.XP_Shop.Bloque_Producto.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Producto.model.Categorias;
import com.XP_Shop.Bloque_Producto.repository.CategoriasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriasService {

    private static final Logger log = LoggerFactory.getLogger(CategoriasService.class);

    @Autowired
    private CategoriasRepository categoriasRepository;

    public List<Categorias> listaCategorias() {
        log.info("Listando todas las categorias");
        return categoriasRepository.findAll();
    }

    public Categorias buscarCategoriasPorId(Integer id) {
        log.info("Buscando categorias por ID: {}", id);
        return categoriasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Las categorias no existen."));
    }

    public Categorias guardarCategorias(Categorias categorias) {
        return categoriasRepository.save(categorias);
    }

    public Categorias actualizarCategorias(Integer id, Categorias categorias) {
        log.info("Actualizando categorias con ID: {}", id);
        Categorias categoriasExistente = categoriasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Las categorias no existen."));

        if (categorias.getCategoria() != null) {
            categoriasExistente.setCategoria(categorias.getCategoria());
        }
        if (categorias.getProducto() != null) {
            categoriasExistente.setProducto(categorias.getProducto());
        }

        log.info("Categorias actualizadas con ID: {}", id);
        return categoriasRepository.save(categoriasExistente);
    }

    public String eliminarCategorias(Integer id) {
        log.info("Eliminando categorias con ID: {}", id);
        try {
            Categorias categorias = categoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar las categorias con ID" + id + " no existen."));
            categoriasRepository.delete(categorias);
            log.info("Categorias eliminadas con ID: {}", id);
            return "La categoria ha sido eliminada correctamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
}
