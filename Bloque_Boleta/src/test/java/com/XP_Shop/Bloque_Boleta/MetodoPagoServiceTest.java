package com.XP_Shop.Bloque_Boleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.XP_Shop.Bloque_Boleta.model.MetodoPago;

import com.XP_Shop.Bloque_Boleta.repository.MetodoPagoRepository;

import com.XP_Shop.Bloque_Boleta.service.MetodoPagoService;

@ExtendWith(MockitoExtension.class)
class MetodoPagoServiceTest {

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @InjectMocks
    private MetodoPagoService metodoPagoService;

    @Test
    void guardarMetodoPago_exito() {

        MetodoPago metodo =
                new MetodoPago();

        metodo.setIdMetodoPago(1);

        metodo.setNombreMetodoPago("Transferencia");

        when(
                metodoPagoRepository
                        .save(any(MetodoPago.class)))
                .thenReturn(metodo);

        var resultado =
                metodoPagoService
                        .guardarMetodoPago(metodo);

        assertNotNull(resultado);

        assertEquals(
                "Transferencia",
                resultado.getNombreMetodoPago());

        verify(
                metodoPagoRepository,
                times(1))
                .save(any(MetodoPago.class));

    }

    @Test
    void buscarMetodoPagoPorId_exito() {

        MetodoPago metodo =
                new MetodoPago();

        metodo.setIdMetodoPago(1);

        metodo.setNombreMetodoPago("Debito");

        when(
                metodoPagoRepository
                        .findById(1))
                .thenReturn(
                        Optional.of(metodo));

        var resultado =
                metodoPagoService
                        .buscarMetodoPagoPorId(1);

        assertNotNull(resultado);

        assertEquals(
                "Debito",
                resultado.getNombreMetodoPago());

    }

    @Test
    void buscarMetodoPago_error() {

        when(
                metodoPagoRepository
                        .findById(99))
                .thenReturn(
                        Optional.empty());

        assertThrows(
                RuntimeException.class,
                () ->
                metodoPagoService
                        .buscarMetodoPagoPorId(99));

    }

}