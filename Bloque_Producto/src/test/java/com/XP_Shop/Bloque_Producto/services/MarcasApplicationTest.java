package com.XP_Shop.Bloque_Producto.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.XP_Shop.Bloque_Producto.model.Marca;
import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.model.Producto;
import com.XP_Shop.Bloque_Producto.repository.MarcasRepository;
import com.XP_Shop.Bloque_Producto.service.MarcasService;
 
@ExtendWith(MockitoExtension.class)
public class MarcasApplicationTest {

    @Mock
    private MarcasRepository marcasRepository;
 
    @InjectMocks
    private MarcasService marcasService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    private Marcas crearMarcasFalsa(Integer id) {
        Marcas marcas = new Marcas();
        marcas.setIdMarcas(id);
        marcas.setMarcas(new ArrayList<>());
        marcas.setProductos(new ArrayList<>());
        return marcas;
    }

 
    @Test
    void testListaMarcasExitoso() {
        Marcas marcas1 = crearMarcasFalsa(0);
        Marcas marcas2 = crearMarcasFalsa(1);
        Marcas marcas3 = crearMarcasFalsa(2);
 
        when(marcasRepository.findAll()).thenReturn(List.of(marcas1, marcas2, marcas3));
 
        List<Marcas> resultado = marcasService.listaMarcas();
 
        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(3, resultado.size(), "Deben retornarse 3 registros de marcas");
        assertEquals(marcas1.getIdMarcas(), resultado.get(0).getIdMarcas(),
                "El ID del primer elemento debe coincidir");
 
        verify(marcasRepository, times(1)).findAll();
    }
 
    @Test
    void testBuscarMarcasPorIdExitoso() {
        Integer id = 1;
        Marcas marcasFalsa = crearMarcasFalsa(id);
 
        when(marcasRepository.findById(id)).thenReturn(Optional.of(marcasFalsa));
 
        Marcas resultado = marcasService.buscarMarcasPorId(id);
 
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(id, resultado.getIdMarcas(),
                "El ID retornado debe coincidir con el buscado");
 
        verify(marcasRepository, times(1)).findById(id);
    }
 
    @Test
    void testBuscarMarcasPorIdNoEncontrado() {
        Integer id = 345;
 
        when(marcasRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> marcasService.buscarMarcasPorId(id),
                "Debe mostrarse la exepcion cuando las marcas no existen");
 
        verify(marcasRepository, times(1)).findById(id);
    }
 
    @Test
    void testGuardarMarcasExitoso() {
        Marcas marcasEntrada = crearMarcasFalsa(null);
        Marcas marcasGuardada = crearMarcasFalsa(10);
 
        when(marcasRepository.save(marcasEntrada)).thenReturn(marcasGuardada);
 
        Marcas resultado = marcasService.guardarMarcas(marcasEntrada);
 
        assertNotNull(resultado, "El resultado guardado no debería ser nulo");
        assertEquals(10, resultado.getIdMarcas(),
                "El ID asignado por la base de datos debe reflejarse en el resultado");
 
        verify(marcasRepository, times(1)).save(marcasEntrada);
    }
 
    @Test
    void testActualizarMarcasExitoso() {
        Integer id = 1;
        Marcas marcasExistente = crearMarcasFalsa(id);
 
        Marcas datosNuevos = new Marcas();
        List<Marca> nuevasMarcas = new ArrayList<>();
        List<Producto> nuevosProductos = new ArrayList<>();
        datosNuevos.setMarcas(nuevasMarcas);
        datosNuevos.setProductos(nuevosProductos);
 
        Marcas marcasActualizada = crearMarcasFalsa(id);
 
        when(marcasRepository.findById(id)).thenReturn(Optional.of(marcasExistente));
        when(marcasRepository.save(marcasExistente)).thenReturn(marcasActualizada);
 
        Marcas resultado = marcasService.actualizarMarcas(id, datosNuevos);
 
        assertNotNull(resultado, "El resultado actualizado no deberia ser nulo");
        assertEquals(id, resultado.getIdMarcas(),
                "El ID debe mantenerse igual tras la actualización");
 
        verify(marcasRepository, times(1)).findById(id);
        verify(marcasRepository, times(1)).save(marcasExistente);
    }
 
    @Test
    void testActualizarMarcasNoEncontrado() {
        Integer id = 856;
        Marcas datosNuevos = crearMarcasFalsa(null);
 
        when(marcasRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> marcasService.actualizarMarcas(id, datosNuevos),
                "Debe mostrarse la exepcion cuando las marcas a actualizar no existen");
 
        verify(marcasRepository, times(1)).findById(id);
    }
 
    @Test
    void testEliminarMarcasExitoso() {
        Integer id = 1;
        Marcas marcasFalsa = crearMarcasFalsa(id);
 
        when(marcasRepository.findById(id)).thenReturn(Optional.of(marcasFalsa));
 
        String resultado = marcasService.eliminarMarcas(id);
 
        assertEquals("Las marcas han sido eliminada correctamente.", resultado,
                "El mensaje de exito debe coincidir con el definido en el service");
 
        verify(marcasRepository, times(1)).findById(id);
        verify(marcasRepository, times(1)).delete(marcasFalsa);
    }
 
    @Test
    void testEliminarMarcasNoEncontrado() {
        Integer id = 834;
 
        when(marcasRepository.findById(id)).thenReturn(Optional.empty());
 
        String resultado = marcasService.eliminarMarcas(id);
 
        assertNotNull(resultado, "El mensaje de error no deberia ser nulo");
        assertTrue(resultado.contains("No se puede eliminar las marcas con ID"),
                "El mensaje debe indicar que las marcas no existen");
 
        verify(marcasRepository, times(1)).findById(id);
    }

}
