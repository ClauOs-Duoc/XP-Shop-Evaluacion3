package com.XP_Shop.Bloque_Boleta.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Boleta.dto.MetodoEnvioDTO;
import com.XP_Shop.Bloque_Boleta.model.Boleta;
import com.XP_Shop.Bloque_Boleta.model.MetodoEnvio;
import com.XP_Shop.Bloque_Boleta.repository.MetodoEnvioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoEnvioService {

    private static final Logger log = LoggerFactory.getLogger(MetodoEnvioService.class);

    private final MetodoEnvioRepository metodoEnvioRepository;

    public MetodoEnvioService(MetodoEnvioRepository metodoEnvioRepository) {
        this.metodoEnvioRepository = metodoEnvioRepository;
    }

    private MetodoEnvioDTO convertirMetodoEnvioADTO(MetodoEnvio metodoEnvio){
        log.info("Convirtiendo metodo de envio a DTO: {}", metodoEnvio.getNombreMetodoEnvio());
        MetodoEnvioDTO dto = new MetodoEnvioDTO();
        dto.setIdMetodoEnvio(metodoEnvio.getIdMetodoEnvio());
        dto.setNombreMetodoEnvio(metodoEnvio.getNombreMetodoEnvio());
        
        if (metodoEnvio.getBoletas() != null){
            List<Integer> idBoletas = new ArrayList<>();
            for(Boleta boleta : metodoEnvio.getBoletas()){
                idBoletas.add(boleta.getIdBoleta());
            }
            dto.setBoletas(idBoletas);
        }

        return dto;
    }

    public List<MetodoEnvioDTO> listarMetodoEnvio() {
        log.info("Listando metodos de envio");
        return metodoEnvioRepository.findAll().stream()
                    .map(this::convertirMetodoEnvioADTO)
                    .toList();
    }
    
    public MetodoEnvioDTO buscarMetodoEnvioPorId(Integer id) {
        log.info("Buscando metodo de envio con ID: {}", id);
        MetodoEnvio metodoEnvio = metodoEnvioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MetodoEnvio no encontrado"));
        return convertirMetodoEnvioADTO(metodoEnvio);
    }

    public MetodoEnvioDTO guardarMetodoEnvio(MetodoEnvio metodoEnvio) {
        log.info("Guardando metodo de envio: {}", metodoEnvio.getNombreMetodoEnvio());
        MetodoEnvio savedMetodoEnvio = metodoEnvioRepository.save(metodoEnvio);
        log.info("Metodo de envio guardado con ID: {}", savedMetodoEnvio.getIdMetodoEnvio());
        return convertirMetodoEnvioADTO(savedMetodoEnvio);
    }

    public MetodoEnvioDTO actualizarMetodoEnvio(Integer id, MetodoEnvio metodoEnvio) {
        log.info("Actualizando metodo de envio con ID: {}", id);
        MetodoEnvio metodoEnvioExistente = metodoEnvioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El metodoEnvio no existe."));
        if (metodoEnvio.getNombreMetodoEnvio() != null) {
            metodoEnvioExistente.setNombreMetodoEnvio(metodoEnvio.getNombreMetodoEnvio());
        }
        if (metodoEnvio.getBoletas() != null) {
            metodoEnvioExistente.setBoletas(metodoEnvio.getBoletas());
        }

        MetodoEnvio updatedMetodoEnvio = metodoEnvioRepository.save(metodoEnvioExistente);
        log.info("Metodo de envio actualizado con ID: {}", updatedMetodoEnvio.getIdMetodoEnvio());
        return convertirMetodoEnvioADTO(updatedMetodoEnvio);
    }

    public Void eliminarMetodoEnvio(Integer id) {
        log.info("Eliminando metodo de envio con ID: {}", id);
        MetodoEnvio metodoEnvio = metodoEnvioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar el metodoEnvio con ID " + id + " no existe."));
        metodoEnvioRepository.delete(metodoEnvio);
        log.info("Metodo de envio eliminado con ID: {}", id);
        return null;
    }
    
}
