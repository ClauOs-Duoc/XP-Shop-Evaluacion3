package com.XP_Shop.Bloque_Producto.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Producto.dto.MarcaDTO;
import com.XP_Shop.Bloque_Producto.model.Marca;
import com.XP_Shop.Bloque_Producto.repository.MarcaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarcaService {

    private static final Logger log = LoggerFactory.getLogger(MarcaService.class);

    @Autowired
    private MarcaRepository marcaRepository;

    private MarcaDTO convertirMarcaADTO(Marca marca){
        log.info("Convirtiendo marca a DTO: {}", marca.getIdMarca());
        MarcaDTO dto = new MarcaDTO();
        dto.setIdMarca(marca.getIdMarca());
        dto.setNombreMarca(marca.getNombreMarca());
        dto.setMarcasId(marca.getMarcas().getIdMarcas());

        return dto;
    }

    public List<MarcaDTO> listarMarca() {
        return marcaRepository.findAll().stream()
                    .map(this::convertirMarcaADTO)
                    .toList();
    }
    
    public MarcaDTO buscarMarcaPorId(Integer id) {
        Marca marca = marcaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marca no encontrado"));
        return convertirMarcaADTO(marca);
    }

    public MarcaDTO guardarMarca(Marca marca) {
        log.info("Guardando marca: {}", marca.getNombreMarca());
        Marca savedMarca = marcaRepository.save(marca);
        log.info("Marca guardada con ID: {}", savedMarca.getIdMarca());
        return convertirMarcaADTO(savedMarca);
    }

    public MarcaDTO actualizarMarca(Integer id, Marca marca) {
        log.info("Actualizando marca con ID: {}", id);
        Marca marcaExistente = marcaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El marca no existe."));
        if (marca.getNombreMarca() != null) {
            marcaExistente.setNombreMarca(marca.getNombreMarca());
        }
        if (marca.getMarcas() != null) {
            marcaExistente.setMarcas(marca.getMarcas());
        }

        Marca updatedMarca = marcaRepository.save(marcaExistente);
        log.info("Marca actualizada con ID: {}", updatedMarca.getIdMarca());
        return convertirMarcaADTO(updatedMarca);
    }

    public Void eliminarMarca(Integer id) {
        log.info("Eliminando marca con ID: {}", id);
        Marca marca = marcaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar la marca con ID " + id + " no existe."));
        marcaRepository.delete(marca);
        log.info("Marca eliminada con ID: {}", id);
        return null;
    }
    
}
