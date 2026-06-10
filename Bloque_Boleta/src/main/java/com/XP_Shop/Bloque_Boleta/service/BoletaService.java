package com.XP_Shop.Bloque_Boleta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Boleta.dto.BoletaDTO;
import com.XP_Shop.Bloque_Boleta.model.Boleta;
import com.XP_Shop.Bloque_Boleta.repository.BoletaRepository;

import java.util.List;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoletaService {

    private static final Logger log = LoggerFactory.getLogger(BoletaService.class);

    @Autowired
    private BoletaRepository boletaRepository;

    private BoletaDTO convertirBoletaADTO(Boleta boleta){
        BoletaDTO dto = new BoletaDTO();
        dto.setIdBoleta(boleta.getIdBoleta());
        dto.setFechaCompra(boleta.getFechaCompra());
        dto.setTotalCompra(boleta.getTotalCompra());
        dto.setMetodoEnvio(boleta.getMetodoEnvio().getNombreMetodoEnvio());
        dto.setMetodoPago(boleta.getMetodoPago().getNombreMetodoPago());
        dto.setDetalleBoleta(boleta.getDetalleBoleta().getIdDetalleBoleta());

        return dto;
    }

    public List<BoletaDTO> listarBoleta() {
        log.info("Listar todas las boletas");
        return boletaRepository.findAll().stream()
                    .map(this::convertirBoletaADTO)
                    .toList();
    }
    
    public BoletaDTO buscarBoletaPorId(Integer id) {
        log.info("Buscar boleta por ID: {}", id);
        Boleta boleta = boletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
        return convertirBoletaADTO(boleta);
    }

    public BoletaDTO guardarBoleta(Boleta boleta) {
        log.info("Guardando nueva boleta");
        Boleta savedBoleta = boletaRepository.save(boleta);
        log.info("Boleta guardada con ID: {}", savedBoleta.getIdBoleta());
        return convertirBoletaADTO(savedBoleta);
    }

    public BoletaDTO actualizarBoleta(Integer id, Boleta boleta) {
        log.info("Actualizando boleta con ID: {}", id);
        Boleta boletaExistente = boletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La boleta no existe."));
        if (boleta.getFechaCompra() != null) {
            boletaExistente.setFechaCompra(boleta.getFechaCompra());
        }
        if (boleta.getMetodoEnvio() != null) {
            boletaExistente.setMetodoEnvio(boleta.getMetodoEnvio());
        }
        if (boleta.getMetodoPago() != null) {
            boletaExistente.setMetodoPago(boleta.getMetodoPago());
        }
        if (boleta.getTotalCompra() != null) {
            boletaExistente.setTotalCompra(boleta.getTotalCompra());
        }
        if (boleta.getDetalleBoleta() != null) {
            boletaExistente.setDetalleBoleta(boleta.getDetalleBoleta());
        }

        log.info("Boleta actualizada con ID: {}", id);
        Boleta updatedBoleta = boletaRepository.save(boletaExistente);
        return convertirBoletaADTO(updatedBoleta);
    }

    public Void eliminarBoleta(Integer id) {
        log.info("Eliminando boleta con ID: {}", id);
        Boleta boleta = boletaRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Boleta con ID {} no encontrada para eliminar", id);
                return new RuntimeException("No se puede eliminar la boleta con ID " + id + " no existe.");
            });
            new RuntimeException("No se puede eliminar la boleta con ID " + id + " no existe.");
        boletaRepository.delete(boleta);
        log.info("Boleta con ID {} eliminada exitosamente", id);
        return null;
    }
    
}
