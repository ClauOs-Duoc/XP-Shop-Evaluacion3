package com.XP_Shop.Bloque_Usuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.XP_Shop.Bloque_Usuario.model.Comuna;
import com.XP_Shop.Bloque_Usuario.repository.ComunaRepository;
import com.XP_Shop.Bloque_Usuario.service.ComunaService;

@ExtendWith(MockitoExtension.class)
public class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @InjectMocks
    private ComunaService comunaService;

    @Test
    void guardarComuna_exito() {
        Comuna comuna = new Comuna();
        comuna.setIdComuna(1);
        comuna.setNombreComuna("Santiago");

        when(comunaRepository.save(any(Comuna.class))).thenReturn(comuna);

        var resultado = comunaService.guardarComuna(comuna);

        assertNotNull(resultado);
        assertEquals("Santiago", resultado.getNombreComuna());
        verify(comunaRepository, times(1)).save(any(Comuna.class));
    }

    @Test
    void buscarComunaPorId_error() {
        when(comunaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            comunaService.buscarComunaPorId(99);
        });

        assertEquals("La comuna no existe.", exception.getMessage());
        verify(comunaRepository, times(1)).findById(99);
    }

    @Test
    void eliminarComuna_exito() {
        Comuna comuna = new Comuna();
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        doNothing().when(comunaRepository).delete(any(Comuna.class));

        var resultado = comunaService.eliminarComuna(1);

        assertEquals("La comuna ha sido eliminada correctamente.", resultado);
        verify(comunaRepository, times(1)).findById(1);
        verify(comunaRepository, times(1)).delete(any(Comuna.class));
    }
}


