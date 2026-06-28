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

import com.XP_Shop.Bloque_Boleta.model.MetodoEnvio;
import com.XP_Shop.Bloque_Boleta.repository.MetodoEnvioRepository;
import com.XP_Shop.Bloque_Boleta.service.MetodoEnvioService;

@ExtendWith(MockitoExtension.class)
class MetodoEnvioServiceTest {

    @Mock
    private MetodoEnvioRepository metodoEnvioRepository;

    @InjectMocks
    private MetodoEnvioService metodoEnvioService;

    @Test
    void guardarMetodoEnvio_exito() {

        MetodoEnvio envio = new MetodoEnvio();

        envio.setIdMetodoEnvio(1);
        envio.setNombreMetodoEnvio("Delivery");

        when(metodoEnvioRepository.save(any(MetodoEnvio.class)))
                .thenReturn(envio);

        var resultado =
                metodoEnvioService.guardarMetodoEnvio(envio);

        assertNotNull(resultado);

        assertEquals(
                "Delivery",
                resultado.getNombreMetodoEnvio());

        verify(
                metodoEnvioRepository,
                times(1))
                .save(any(MetodoEnvio.class));

    }

    @Test
    void buscarMetodoEnvio_error() {

        when(
                metodoEnvioRepository.findById(100))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () ->
                        metodoEnvioService
                        .buscarMetodoEnvioPorId(100));

        assertEquals(
                "MetodoEnvio no encontrado",
                exception.getMessage());

    }

}