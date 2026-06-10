package com.XP_Shop.Bloque_Usuario.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Usuario.dto.ComunaDTO;
import com.XP_Shop.Bloque_Usuario.model.Comuna;
import com.XP_Shop.Bloque_Usuario.model.Usuario;
import com.XP_Shop.Bloque_Usuario.repository.ComunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {

    private static final Logger log = LoggerFactory.getLogger(ComunaService.class);

    @Autowired
    private ComunaRepository comunaRepository;

    private ComunaDTO convertirComunaADTO(Comuna comuna){
        log.info("Convirtiendo comuna a DTO: {}", comuna.getIdComuna());
        ComunaDTO dto = new ComunaDTO();
        dto.setIdComuna(comuna.getIdComuna());
        dto.setNombreComuna(comuna.getNombreComuna());
        dto.setNombreRegion(comuna.getRegion().getNombreRegion());

        if (comuna.getUsuario() != null){
            List<String> usuarios = new ArrayList<>();
            for(Usuario usuario : comuna.getUsuario()){
                usuarios.add(usuario.getNombreUsuario());
            }
            dto.setNombreUsuarios(usuarios);
        }

        return dto;
    }

    public List<ComunaDTO> listarComuna() {
        log.info("Listando todas las comunas");
        return comunaRepository.findAll().stream()
                    .map(this::convertirComunaADTO)
                    .toList();
    }
    
    public ComunaDTO buscarComunaPorId(Integer id) {
        log.info("Buscando comuna por ID: {}", id);
        Comuna comuna = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comuna no encontrado"));
        return convertirComunaADTO(comuna);
    }

    public ComunaDTO guardarComuna(Comuna comuna) {
        log.info("Guardando comuna: {}", comuna.getNombreComuna());
        Comuna savedComuna = comunaRepository.save(comuna);
        log.info("Comuna guardada con ID: {}", savedComuna.getIdComuna());
        return convertirComunaADTO(savedComuna);
    }

    public ComunaDTO actualizarComuna(Integer id, Comuna comuna) {
        log.info("Actualizando comuna con ID: {}", id);
        Comuna comunaExistente = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La comuna no existe."));
        if (comuna.getNombreComuna() != null) {
            comunaExistente.setNombreComuna(comuna.getNombreComuna());
        }
        if (comuna.getRegion() != null) {
            comunaExistente.setRegion(comuna.getRegion());
        }
        if (comuna.getUsuario() != null) {
            comunaExistente.setUsuario(comuna.getUsuario());
        }

        Comuna updatedComuna = comunaRepository.save(comunaExistente);
        log.info("Comuna actualizada con ID: {}", updatedComuna.getIdComuna());
        return convertirComunaADTO(updatedComuna);
    }

    public Void eliminarComuna(Integer id) {
        log.info("Eliminando comuna con ID: {}", id);
        Comuna comuna = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar la comuna con ID " + id + " no existe."));
        comunaRepository.delete(comuna);
        log.info("Comuna eliminada con ID: {}", id);
        return null;
    }
    
}
