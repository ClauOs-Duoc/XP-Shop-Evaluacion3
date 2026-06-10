package com.XP_Shop.Bloque_Usuario.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Usuario.dto.RegionDTO;
import com.XP_Shop.Bloque_Usuario.model.Comuna;
import com.XP_Shop.Bloque_Usuario.model.Region;
import com.XP_Shop.Bloque_Usuario.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegionService.class);

    @Autowired
    private RegionRepository regionRepository;

    private RegionDTO convertirRegionADTO(Region region){
        log.info("Convirtiendo region a DTO: {}", region.getIdRegion());
        RegionDTO dto = new RegionDTO();
        dto.setIdRegion(region.getIdRegion());
        dto.setNombreRegion(region.getNombreRegion());
        if (region.getComunas() != null){
            List<String> nombresComunas = new ArrayList<>();
            for(Comuna comuna : region.getComunas()){
                nombresComunas.add(comuna.getNombreComuna());
            }
            log.info("Comunas encontradas para la region {}: {}", region.getIdRegion(), nombresComunas);
            dto.setNombreComunas(nombresComunas);
        }

        return dto;
    }

    public List<RegionDTO> listarRegion() {
        log.info("Listando regiones");
        return regionRepository.findAll().stream()
                    .map(this::convertirRegionADTO)
                    .toList();
    }
    
    public RegionDTO buscarRegionPorId(Integer id) {
        log.info("Buscando region con ID: {}", id);
        Region region = regionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Region no encontrado"));
        return convertirRegionADTO(region);
    }

    public RegionDTO guardarRegion(Region region) {
        log.info("Guardando region: {}", region.getNombreRegion());
        Region savedRegion = regionRepository.save(region);
        log.info("Region guardada con ID: {}", savedRegion.getIdRegion());
        return convertirRegionADTO(savedRegion);
    }

    public RegionDTO actualizarRegion(Integer id, Region region) {
        log.info("Actualizando region con ID: {}", id);
        Region regionExistente = regionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La region no existe."));
        if (region.getNombreRegion() != null) {
            regionExistente.setNombreRegion(region.getNombreRegion());
        }
        if (region.getComunas() != null) {
            regionExistente.setComunas(region.getComunas());
        }
        log.info("Region actualizada con ID: {}", id);
        Region updatedRegion = regionRepository.save(regionExistente);
        return convertirRegionADTO(updatedRegion);
    }

    public Void eliminarRegion(Integer id) {
        log.info("Eliminando region con ID: {}", id);
        Region region = regionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar la region con ID " + id + " no existe."));
        regionRepository.delete(region);
        log.info("Region eliminada con ID: {}", id);
        return null;
    }
    
}
