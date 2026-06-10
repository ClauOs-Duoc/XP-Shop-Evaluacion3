package com.XP_Shop.Bloque_Producto.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.repository.MarcasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarcasService {

    private static final Logger log = LoggerFactory.getLogger(MarcasService.class);

    @Autowired
    private MarcasRepository marcasRepository;

    public List<Marcas> listaMarcas() {
        return marcasRepository.findAll();
    }

    public Marcas buscarMarcasPorId(Integer id) {
        return marcasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Las marcas no existe."));
    }

    public Marcas guardarMarcas(Marcas marcas) {
        log.info("Guardando marcas: {}", marcas.getMarcas());
        Marcas savedMarcas = marcasRepository.save(marcas);
        log.info("Marcas guardadas con ID: {}", savedMarcas.getIdMarcas());
        return savedMarcas;
    }

    public Marcas actualizarMarcas(Integer id, Marcas marcas) {
        log.info("Actualizando marcas con ID: {}", id);
        Marcas marcasExistente = marcasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Las marcas no existe."));

        if (marcas.getMarcas() != null) {
            marcasExistente.setMarcas(marcas.getMarcas());
        }
        if (marcas.getProductos() != null) {
            marcasExistente.setProductos(marcas.getProductos());
        }

        log.info("Marcas actualizadas con ID: {}", marcasExistente.getIdMarcas());
        return marcasRepository.save(marcasExistente);
    }

    public String eliminarMarcas(Integer id) {
        try {
            Marcas marcas = marcasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar las marcas con ID" + id + " no existe."));
            log.info("Eliminando marcas con ID: {}", id);
            marcasRepository.delete(marcas);
            log.info("Marcas eliminadas con ID: {}", id);
            return "Las marcas han sido eliminada correctamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
}
