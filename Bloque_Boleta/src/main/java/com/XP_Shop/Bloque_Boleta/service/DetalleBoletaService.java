package com.XP_Shop.Bloque_Boleta.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Boleta.dto.DetalleBoletaDTO;
import com.XP_Shop.Bloque_Boleta.model.DetalleBoleta;
import com.XP_Shop.Bloque_Boleta.repository.DetalleBoletaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleBoletaService {

    private static final Logger log = LoggerFactory.getLogger(DetalleBoletaService.class);


    private final DetalleBoletaRepository detalleBoletaRepository;

    DetalleBoletaService(DetalleBoletaRepository detalleBoletaRepository) {
        this.detalleBoletaRepository = detalleBoletaRepository;
    }

    private DetalleBoletaDTO convertirDetalleBoletaADTO(DetalleBoleta detalleBoleta){
        DetalleBoletaDTO dto = new DetalleBoletaDTO();
        dto.setIdDetalleBoleta(detalleBoleta.getIdDetalleBoleta());
        dto.setCantidad(detalleBoleta.getCantidad());
        dto.setSubtotal(detalleBoleta.getSubtotal());
        dto.setBoleta(detalleBoleta.getBoleta().getIdBoleta());
        dto.setProductos(detalleBoleta.getProductos().getIdProducto());

        return dto;
    }

    public List<DetalleBoletaDTO> listarDetalleBoleta() {
        log.info("Listar todos los detalles de boleta");
        return detalleBoletaRepository.findAll().stream()
                    .map(this::convertirDetalleBoletaADTO)
                    .toList();
    }
    
    public DetalleBoletaDTO buscarDetalleBoletaPorId(Integer id) {
        log.info("Buscar detalle de boleta por ID: {}", id);
        DetalleBoleta detalleBoleta = detalleBoletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DetalleBoleta no encontrado"));
        return convertirDetalleBoletaADTO(detalleBoleta);
    }

    public DetalleBoletaDTO guardarDetalleBoleta(DetalleBoleta detalleBoleta) {
        log.info("Guardando nuevo detalle de boleta");
        DetalleBoleta savedDetalleBoleta = detalleBoletaRepository.save(detalleBoleta);
        log.info("Detalle de boleta guardado con ID: {}", savedDetalleBoleta.getIdDetalleBoleta());
        return convertirDetalleBoletaADTO(savedDetalleBoleta);
    }

    public DetalleBoletaDTO actualizarDetalleBoleta(Integer id, DetalleBoleta detalleBoleta) {
        log.info("Actualizando detalle de boleta con ID: {}", id);
        DetalleBoleta detalleBoletaExistente = detalleBoletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El detalleBoleta no existe."));
        if (detalleBoleta.getCantidad() != null) {
            detalleBoletaExistente.setCantidad(detalleBoleta.getCantidad());
        }
        if (detalleBoleta.getSubtotal() != null) {
            detalleBoletaExistente.setSubtotal(detalleBoleta.getSubtotal());
        }
        if (detalleBoleta.getBoleta() != null) {
            detalleBoletaExistente.setBoleta(detalleBoleta.getBoleta());
        }
        if (detalleBoleta.getProductos() != null) {
            detalleBoletaExistente.setProductos(detalleBoleta.getProductos());
        }

        DetalleBoleta updatedDetalleBoleta = detalleBoletaRepository.save(detalleBoletaExistente);
        log.info("Detalle de boleta actualizado con ID: {}", id);
        return convertirDetalleBoletaADTO(updatedDetalleBoleta);
    }

    public Void eliminarDetalleBoleta(Integer id) {
        log.info("Eliminando detalle de boleta con ID: {}", id);
        DetalleBoleta detalleBoleta = detalleBoletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar el detalleBoleta con ID " + id + " no existe."));
        detalleBoletaRepository.delete(detalleBoleta);
        log.info("Detalle de boleta con ID {} eliminado exitosamente", id);
        return null;
    }
    
}
