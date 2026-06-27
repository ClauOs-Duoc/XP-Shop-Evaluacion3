package com.XP_Shop.Bloque_Producto.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
 
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.XP_Shop.Bloque_Producto.dto.MarcaDTO;
import com.XP_Shop.Bloque_Producto.model.Marca;
import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.repository.MarcaRepository;
import com.XP_Shop.Bloque_Producto.service.MarcaService;

import net.datafaker.Faker;
 
@ExtendWith(MockitoExtension.class)
public class MarcaApplicationTest {

    @Mock
    private MarcaRepository marcaRepository;
 
    @InjectMocks
    private MarcaService marcaService;
 
    private Faker faker = new Faker();
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Marca crearMarcaFalsa(Integer id) {
        Marcas marcas = new Marcas();
        marcas.setIdMarcas(1);
        marcas.setMarcas(null);
        marcas.setProductos(null);

        Marca marca = new Marca();
        marca.setIdMarca(id);
        marca.setNombreMarca(faker.company().name());
        marca.setMarcas(marcas); 
        return marca;
    }
 
    @Test
    void testListarMarcaExitoso() {
        Marca marca1 = crearMarcaFalsa(0);
        Marca marca2 = crearMarcaFalsa(1);
        Marca marca3 = crearMarcaFalsa(2);
 
        when(marcaRepository.findAll()).thenReturn(List.of(marca1, marca2, marca3));
 
        List<MarcaDTO> resultado = marcaService.listarMarca();

        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(3, resultado.size(), "Deben retornarse las 3 marca");
        assertEquals(marca1.getNombreMarca(), resultado.get(0).getNombreMarca(),
            "El nombre de la primera marca debe coincidir");
 
        verify(marcaRepository, times(1)).findAll();
    }
 
    @Test
    void testBuscarMarcaPorIdExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Marca marcaFalsa = crearMarcaFalsa(id);
 
        when(marcaRepository.findById(id)).thenReturn(Optional.of(marcaFalsa));
 
        MarcaDTO resultado = marcaService.buscarMarcaPorId(id);
 
        assertNotNull(resultado, "El DTO no debería ser nulo");
        assertEquals(id, resultado.getIdMarca(),
                "El ID del DTO debe coincidir con el buscado");
        assertEquals(marcaFalsa.getNombreMarca(), resultado.getNombreMarca(),
                "El nombre transformado al DTO debe coincidir con el de la base de datos");
 
        verify(marcaRepository, times(1)).findById(id);
    }
 
    @Test
    void testBuscarMarcaPorIdNoEncontrado() {
        Integer id = 745;
 
        when(marcaRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> marcaService.buscarMarcaPorId(id),
                "Debe mostrarse la exepcion cuando la marca no existe");
 
        verify(marcaRepository, times(1)).findById(id);
    }
 
    @Test
    void testGuardarMarcaExitoso() {
        Marca marcaEntrada = crearMarcaFalsa(null);
        Marca marcaGuardada = crearMarcaFalsa(10);
        marcaGuardada.setNombreMarca(marcaEntrada.getNombreMarca());
 
        when(marcaRepository.save(marcaEntrada)).thenReturn(marcaGuardada);
 
        MarcaDTO resultado = marcaService.guardarMarca(marcaEntrada);
 
        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(marcaGuardada.getIdMarca(), resultado.getIdMarca(),
                "El ID asignado por la base de datos debe reflejarse en el DTO");
        assertEquals(marcaEntrada.getNombreMarca(), resultado.getNombreMarca(),
                "El nombre guardado debe coincidir con el ingresado");
 
        verify(marcaRepository, times(1)).save(marcaEntrada);
    }
 
    @Test
    void testActualizarMarcaExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Marca marcaExistente = crearMarcaFalsa(id);
 
        Marca datosNuevos = new Marca();
        datosNuevos.setNombreMarca(faker.company().name());
        datosNuevos.setMarcas(new Marcas());
 
        Marca marcaActualizada = new Marca();
        marcaActualizada.setIdMarca(id);
        marcaActualizada.setNombreMarca(datosNuevos.getNombreMarca());
        marcaActualizada.setMarcas(datosNuevos.getMarcas());
 
        when(marcaRepository.findById(id)).thenReturn(Optional.of(marcaExistente));
        when(marcaRepository.save(marcaExistente)).thenReturn(marcaActualizada);
 
        MarcaDTO resultado = marcaService.actualizarMarca(id, datosNuevos);
 
        assertNotNull(resultado, "El DTO actualizado no deberia ser nulo");
        assertEquals(datosNuevos.getNombreMarca(), resultado.getNombreMarca(),
                "El nombre actualizado debe coincidir con los datos enviados");
 
        verify(marcaRepository, times(1)).findById(id);
        verify(marcaRepository, times(1)).save(marcaExistente);
    }
 
    @Test
    void testActualizarMarcaNoEncontrado() {
        Integer id = 646;
        Marca datosNuevos = crearMarcaFalsa(null);
 
        when(marcaRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> marcaService.actualizarMarca(id, datosNuevos),
                "Debe mostrarse una exepcion cuando la marca a actualizar no existe");
 
        verify(marcaRepository, times(1)).findById(id);
    }
 
    @Test
    void testEliminarMarcaExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Marca marcaFalsa = crearMarcaFalsa(id);
 
        when(marcaRepository.findById(id)).thenReturn(Optional.of(marcaFalsa));
 
        Void resultado = marcaService.eliminarMarca(id);
 
        assertEquals(null, resultado, "Eliminar marca debe retornar null (Void)");
 
        verify(marcaRepository, times(1)).findById(id);
        verify(marcaRepository, times(1)).delete(marcaFalsa);
    }
 
    @Test
    void testEliminarMarca_NoEncontrado() {
        Integer id = 536;
 
        when(marcaRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> marcaService.eliminarMarca(id),
                "Debe mostrarse un exepcion cuando la marca a eliminar no existe");
 
        verify(marcaRepository, times(1)).findById(id);
    }

}
