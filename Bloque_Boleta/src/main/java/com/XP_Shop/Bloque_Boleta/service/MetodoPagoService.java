package com.XP_Shop.Bloque_Boleta.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Boleta.dto.MetodoPagoDTO;
import com.XP_Shop.Bloque_Boleta.model.Boleta;
import com.XP_Shop.Bloque_Boleta.model.MetodoPago;
import com.XP_Shop.Bloque_Boleta.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoPagoService {

    private static final Logger log = LoggerFactory.getLogger(MetodoPagoService.class);

    private final MetodoPagoRepository metodoPagoRepository;

    MetodoPagoService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    private MetodoPagoDTO convertirMetodoPagoADTO(MetodoPago metodoPago){
        log.info("Convirtiendo metodo de pago a DTO: {}", metodoPago.getNombreMetodoPago());
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setIdMetodoPago(metodoPago.getIdMetodoPago());
        dto.setNombreMetodoPago(metodoPago.getNombreMetodoPago());

        if (metodoPago.getBoletas() != null){
            List<Integer> idBoletas = new ArrayList<>();
            for(Boleta boleta : metodoPago.getBoletas()){
                idBoletas.add(boleta.getIdBoleta());
            }
            dto.setBoletas(idBoletas);
        }

        return dto;
    }

    public List<MetodoPagoDTO> listarMetodoPago() {
        log.info("Listando metodos de pago");
        return metodoPagoRepository.findAll().stream()
                    .map(this::convertirMetodoPagoADTO)
                    .toList();
    }
    
    public MetodoPagoDTO buscarMetodoPagoPorId(Integer id) {
        log.info("Buscando metodo de pago con ID: {}", id);
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MetodoPago no encontrado"));
        return convertirMetodoPagoADTO(metodoPago);
    }

    public MetodoPagoDTO guardarMetodoPago(MetodoPago metodoPago) {
        log.info("Guardando metodo de pago: {}", metodoPago.getNombreMetodoPago());
        MetodoPago savedMetodoPago = metodoPagoRepository.save(metodoPago);
        log.info("Metodo de pago guardado con ID: {}", savedMetodoPago.getIdMetodoPago());
        return convertirMetodoPagoADTO(savedMetodoPago);
    }

    public MetodoPagoDTO actualizarMetodoPago(Integer id, MetodoPago metodoPago) {
        log.info("Actualizando metodo de pago con ID: {}", id);
        MetodoPago metodoPagoExistente = metodoPagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El metodoPago no existe."));
        if (metodoPago.getNombreMetodoPago() != null) {
            metodoPagoExistente.setNombreMetodoPago(metodoPago.getNombreMetodoPago());
        }
        if (metodoPago.getBoletas() != null) {
            metodoPagoExistente.setBoletas(metodoPago.getBoletas());
        }

        MetodoPago updatedMetodoPago = metodoPagoRepository.save(metodoPagoExistente);
        log.info("Metodo de pago actualizado con ID: {}", updatedMetodoPago.getIdMetodoPago());
        return convertirMetodoPagoADTO(updatedMetodoPago);
    }

    public Void eliminarMetodoPago(Integer id) {
        log.info("Eliminando metodo de pago con ID: {}", id);
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar el metodoPago con ID " + id + " no existe."));
        metodoPagoRepository.delete(metodoPago);
        log.info("Metodo de pago eliminado con ID: {}", id);
        return null;
    }
    
}
