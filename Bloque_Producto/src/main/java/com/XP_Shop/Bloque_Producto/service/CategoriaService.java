package com.XP_Shop.Bloque_Producto.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Producto.dto.CategoriaDTO;
import com.XP_Shop.Bloque_Producto.model.Categoria;
import com.XP_Shop.Bloque_Producto.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {

    private static final Logger log = LoggerFactory.getLogger(CategoriaService.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    private CategoriaDTO convertirCategoriaADTO(Categoria categoria){
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setIdCategorias(categoria.getCategorias().getIdCategorias());

        return dto;
    }

    public List<CategoriaDTO> listarCategoria() {
        log.info("Listando todas las categorias");
        return categoriaRepository.findAll().stream()
                    .map(this::convertirCategoriaADTO)
                    .toList();
    }
    
    public CategoriaDTO buscarCategoriaPorId(Integer id) {
        log.info("Buscando categoria por ID: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return convertirCategoriaADTO(categoria);
    }

    public CategoriaDTO guardarCategoria(Categoria categoria) {
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertirCategoriaADTO(savedCategoria);
    }

    public CategoriaDTO actualizarCategoria(Integer id, Categoria categoria) {
        log.info("Actualizando categoria con ID: {}", id);
        Categoria categoriaExistente = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La categoria no existe."));
        if (categoria.getNombreCategoria() != null) {
            categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
        }
        if (categoria.getCategorias() != null) {
            categoriaExistente.setCategorias(categoria.getCategorias());
        }

        Categoria updatedCategoria = categoriaRepository.save(categoriaExistente);
        log.info("Categoria actualizada con ID: {}", id);
        return convertirCategoriaADTO(updatedCategoria);
    }

    public Void eliminarCategoria(Integer id) {
        log.info("Eliminando categoria con ID: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar la categoria con ID " + id + " no existe."));
        categoriaRepository.delete(categoria);
        return null;
    }
    
}
