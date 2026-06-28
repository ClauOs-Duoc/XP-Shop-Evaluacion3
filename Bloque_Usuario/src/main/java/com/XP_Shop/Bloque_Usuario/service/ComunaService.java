package com.XP_Shop.Bloque_Usuario.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Usuario.model.Comuna;
import com.XP_Shop.Bloque_Usuario.repository.ComunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {

    private static final Logger log = LoggerFactory.getLogger(ComunaService.class);

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> listaComuna() {
        log.info("Listando todas las comunas");
        return comunaRepository.findAll();
    }

    public Comuna buscarComunaPorId(Integer id) {
        log.info("Buscando comuna por ID: {}", id);
        return comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La comuna no existe."));
    }

    public Comuna guardarComuna(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public Comuna actualizarComuna(Integer id, Comuna comuna) {
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

        log.info("Comuna actualizada con ID: {}", id);
        return comunaRepository.save(comunaExistente);
    }

    public String eliminarComuna(Integer id) {
        log.info("Eliminando comuna con ID: {}", id);
        try {
            Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar la comuna con ID " + id + " no existe."));
            comunaRepository.delete(comuna);
            log.info("Comuna eliminada con ID: {}", id);
            return "La comuna ha sido eliminada correctamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}
