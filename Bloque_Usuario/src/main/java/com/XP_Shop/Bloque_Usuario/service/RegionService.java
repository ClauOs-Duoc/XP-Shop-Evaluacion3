package com.XP_Shop.Bloque_Usuario.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Usuario.model.Region;
import com.XP_Shop.Bloque_Usuario.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {

    private static final Logger log = LoggerFactory.getLogger(RegionService.class);

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> listaRegion() {
        log.info("Listando todas las regiones");
        return regionRepository.findAll();
    }

    public Region buscarRegionPorId(Integer id) {
        log.info("Buscando region por ID: {}", id);
        return regionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La region no existe"));
    }

    public Region guardarRegion(Region region) {
        return regionRepository.save(region);
    }

    public Region actualizarRegion(Integer id, Region region) {
        log.info("Actualizando region con ID: {}", id);
        Region regionExistente = regionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La region no existe"));

        if (region.getNombreRegion() != null) {
            regionExistente.setNombreRegion(region.getNombreRegion());
        }
        if (region.getComunas() != null) {
            regionExistente.setComunas(region.getComunas());
        }

        log.info("Region actualizada con ID: {}", id);
        return regionRepository.save(regionExistente);
    }

    public String eliminarRegion(Integer id) {
        log.info("Eliminando region con ID: {}", id);
        try {
            Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar la region con ID " + id + " no existe"));
            regionRepository.delete(region);
            log.info("Region eliminada con ID: {}", id);
            return "La region ha sido eliminada correctamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
}
