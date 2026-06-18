package com.XP_Shop.Bloque_Boleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.XP_Shop.Bloque_Boleta.model.Boleta;
import com.XP_Shop.Bloque_Boleta.model.DetalleBoleta;
import com.XP_Shop.Bloque_Boleta.model.MetodoEnvio;
import com.XP_Shop.Bloque_Boleta.model.MetodoPago;
import com.XP_Shop.Bloque_Boleta.repository.BoletaRepository;
import com.XP_Shop.Bloque_Boleta.service.BoletaService;

@ExtendWith(MockitoExtension.class)
class BoletaServiceTest {

    @Mock
    private BoletaRepository boletaRepository;

    @InjectMocks
    private BoletaService boletaService;

    @Test
    void guardarBoleta_exito() {

        MetodoEnvio envio = new MetodoEnvio();
        envio.setNombreMetodoEnvio("Delivery");

        MetodoPago pago = new MetodoPago();
        pago.setNombreMetodoPago("Debito");

        DetalleBoleta detalle = new DetalleBoleta();
        detalle.setIdDetalleBoleta(1);

        Boleta boleta = new Boleta();
        boleta.setIdBoleta(1);
        boleta.setFechaCompra(LocalDate.now());
        boleta.setTotalCompra(5000.0);
        boleta.setMetodoEnvio(envio);
        boleta.setMetodoPago(pago);
        boleta.setDetalleBoleta(detalle);

        when(boletaRepository.save(any(Boleta.class)))
                .thenReturn(boleta);

        var resultado = boletaService.guardarBoleta(boleta);

        assertNotNull(resultado);
        assertEquals(5000.0, resultado.getTotalCompra());

        verify(boletaRepository, times(1))
                .save(any(Boleta.class));
    }
    
    @Test
    void guardarBoleta_totalCero_excepcion() {

    Boleta boleta = new Boleta();
    boleta.setTotalCompra(0.0);

    RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> boletaService.guardarBoleta(boleta));

    assertEquals(
            "El total de compra debe ser mayor a cero.",
            exception.getMessage());

    verify(boletaRepository, never()).save(any());
    }
}
