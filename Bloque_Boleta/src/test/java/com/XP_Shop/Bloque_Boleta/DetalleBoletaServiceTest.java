package com.XP_Shop.Bloque_Boleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.XP_Shop.Bloque_Boleta.model.Boleta;
import com.XP_Shop.Bloque_Boleta.model.DetalleBoleta;

import com.XP_Shop.Bloque_Boleta.repository.DetalleBoletaRepository;

import com.XP_Shop.Bloque_Boleta.service.DetalleBoletaService;

@ExtendWith(MockitoExtension.class)
class DetalleBoletaServiceTest {

    @Mock
    private DetalleBoletaRepository detalleBoletaRepository;

    @InjectMocks
    private DetalleBoletaService detalleBoletaService;

    @Test
    void guardarDetalleBoleta_exito() {

        Boleta boleta = new Boleta();
        boleta.setIdBoleta(1);

        DetalleBoleta detalle =
                new DetalleBoleta();

        detalle.setIdDetalleBoleta(1);
        detalle.setCantidad(2);
        detalle.setSubtotal(12000.0);
        detalle.setBoleta(boleta);
        detalle.setProductoId(10);

        when(
                detalleBoletaRepository
                        .save(any(DetalleBoleta.class)))
                .thenReturn(detalle);

        var resultado =
                detalleBoletaService
                        .guardarDetalleBoleta(detalle);

        assertNotNull(resultado);

        assertEquals(
                12000.0,
                resultado.getSubtotal());

        verify(
                detalleBoletaRepository,
                times(1))
                .save(any(DetalleBoleta.class));

    }

    @Test
    void buscarDetalleBoletaPorId_exito() {

        Boleta boleta = new Boleta();

        boleta.setIdBoleta(1);

        DetalleBoleta detalle =
                new DetalleBoleta();

        detalle.setIdDetalleBoleta(1);

        detalle.setCantidad(1);

        detalle.setSubtotal(5000.0);

        detalle.setBoleta(boleta);

        detalle.setProductoId(5);

        when(
                detalleBoletaRepository
                        .findById(1))
                .thenReturn(
                        java.util.Optional.of(detalle));

        var resultado =
                detalleBoletaService
                        .buscarDetalleBoletaPorId(1);

        assertNotNull(resultado);

        assertEquals(
                5000.0,
                resultado.getSubtotal());

    }

    @Test
    void buscarDetalleBoleta_error() {

        when(
                detalleBoletaRepository
                        .findById(99))
                .thenReturn(
                        java.util.Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () ->
                        detalleBoletaService
                                .buscarDetalleBoletaPorId(99));

        assertEquals(
                "DetalleBoleta no encontrado",
                exception.getMessage());

    }

}