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

import com.XP_Shop.Bloque_Usuario.model.Region;
import com.XP_Shop.Bloque_Usuario.repository.RegionRepository;
import com.XP_Shop.Bloque_Usuario.service.RegionService;

@ExtendWith(MockitoExtension.class)
public class RegionServiceTest {


    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    @Test
    void guardarRegion_exito() {
        Region region = new Region();
        region.setIdRegion(1);
        region.setNombreRegion("Metropolitana");

        when(regionRepository.save(any(Region.class))).thenReturn(region);

        var resultado = regionService.guardarRegion(region);

        assertNotNull(resultado);
        assertEquals("Metropolitana", resultado.getNombreRegion());
        verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    void buscarRegionPorId_error() {
        when(regionRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.buscarRegionPorId(99);
        });

        assertEquals("La region no existe.", exception.getMessage());
        verify(regionRepository, times(1)).findById(99);
    }

    @Test
    void eliminarRegion_exito() {
        Region region = new Region();
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        doNothing().when(regionRepository).delete(any(Region.class));

        var resultado = regionService.eliminarRegion(1);

        assertEquals("La region ha sido eliminada correctamente.", resultado);
        verify(regionRepository, times(1)).findById(1);
        verify(regionRepository, times(1)).delete(any(Region.class));
    }
}

