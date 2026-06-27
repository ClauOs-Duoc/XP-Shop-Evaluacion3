package com.XP_Shop.Bloque_Producto.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
 
import com.XP_Shop.Bloque_Producto.model.Categorias;
import com.XP_Shop.Bloque_Producto.repository.CategoriasRepository;
import com.XP_Shop.Bloque_Producto.service.CategoriasService;
 
@ExtendWith(MockitoExtension.class)
public class CategoriasApplicationTest {

    @Mock
    private CategoriasRepository categoriasRepository;
 
    @InjectMocks
    private CategoriasService categoriasService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    private Categorias crearCategoriasFalsa(Integer id) {
        Categorias categorias = new Categorias();
        categorias.setIdCategorias(id);
        categorias.setCategoria(new ArrayList<>());
        categorias.setProducto(new ArrayList<>());
        return categorias;
    }
 
    @Test
    void testListaCategoriasExitoso() {
        Categorias categorias1 = crearCategoriasFalsa(0);
        Categorias categorias2 = crearCategoriasFalsa(1);
        Categorias categorias3 = crearCategoriasFalsa(2);
 
        when(categoriasRepository.findAll()).thenReturn(List.of(categorias1, categorias2, categorias3));
 
        List<Categorias> resultado = categoriasService.listaCategorias();
 
        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(3, resultado.size(), "Deben retornarse 3 registros de categorias");
 
        verify(categoriasRepository, times(1)).findAll();
    }
 
    @Test
    void testBuscarCategoriasPorIdExitoso() {
        Integer id = 1;
        Categorias categoriasFalsa = crearCategoriasFalsa(id);
 
        when(categoriasRepository.findById(id)).thenReturn(Optional.of(categoriasFalsa));
 
        Categorias resultado = categoriasService.buscarCategoriasPorId(id);
 
        assertNotNull(resultado, "El resultado no deberia ser nulo");
        assertEquals(id, resultado.getIdCategorias(),
                "El ID retornado debe coincidir con el buscado");
 
        verify(categoriasRepository, times(1)).findById(id);
    }
 
    @Test
    void testBuscarCategoriasPorIdNoEncontrado() {
        Integer id = 69;
 
        when(categoriasRepository.findById(id)).thenReturn(Optional.empty());
 
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> categoriasService.buscarCategoriasPorId(id),
                "Debe mostrarse la exepcion cuando las categorias no existen");
 
        verify(categoriasRepository, times(1)).findById(id);
    }
 
    @Test
    void testGuardarCategoriasExitoso() {
        Categorias categoriasEntrada = crearCategoriasFalsa(null);
        Categorias categoriasGuardada = crearCategoriasFalsa(10);
 
        when(categoriasRepository.save(categoriasEntrada)).thenReturn(categoriasGuardada);
 
        Categorias resultado = categoriasService.guardarCategorias(categoriasEntrada);
 
        assertNotNull(resultado, "El resultado guardado no debería ser nulo");
        assertEquals(10, resultado.getIdCategorias(),
                "El ID asignado por la base de datos debe reflejarse en el resultado");
 
        verify(categoriasRepository, times(1)).save(categoriasEntrada);
    }
 
    @Test
    void testActualizarCategoriasExitoso() {
        Integer id = 1;
        Categorias categoriasExistente = crearCategoriasFalsa(id);
        Categorias datosNuevos = crearCategoriasFalsa(null);
        Categorias categoriasActualizada = crearCategoriasFalsa(id);
 
        when(categoriasRepository.findById(id)).thenReturn(Optional.of(categoriasExistente));
        when(categoriasRepository.save(categoriasExistente)).thenReturn(categoriasActualizada);
 
        Categorias resultado = categoriasService.actualizarCategorias(id, datosNuevos);
 
        assertNotNull(resultado, "El resultado actualizado no deberia ser nulo");
        assertEquals(id, resultado.getIdCategorias(),
                "El ID debe mantenerse igual tras la actualización");
 
        verify(categoriasRepository, times(1)).findById(id);
        verify(categoriasRepository, times(1)).save(categoriasExistente);
    }
 
    @Test
    void testActualizarCategoriasNoEncontrado() {
        Integer id = 505;
        Categorias datosNuevos = crearCategoriasFalsa(null);
 
        when(categoriasRepository.findById(id)).thenReturn(Optional.empty());
 
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
            () -> categoriasService.actualizarCategorias(id, datosNuevos),
            "Debe mostrarse una exepcion cuando las categorias a actualizar no existen");
 
        verify(categoriasRepository, times(1)).findById(id);
    }
 
    @Test
    void testEliminarCategoriasExitoso() {
        Integer id = 1;
        Categorias categoriasFalsa = crearCategoriasFalsa(id);
 
        when(categoriasRepository.findById(id)).thenReturn(Optional.of(categoriasFalsa));
 
        String resultado = categoriasService.eliminarCategorias(id);
 
        assertEquals("La categoria ha sido eliminada correctamente.", resultado,
            "El mensaje de exito debe coincidir con el definido en el service");
 
        verify(categoriasRepository, times(1)).findById(id);
        verify(categoriasRepository, times(1)).delete(categoriasFalsa);
    }
 
    @Test
    void testEliminarCategoriasNoEncontrado() {
        Integer idInexistente = 999;
 
        when(categoriasRepository.findById(idInexistente)).thenReturn(Optional.empty());
 
        String resultado = categoriasService.eliminarCategorias(idInexistente);
 
        assertNotNull(resultado, "El mensaje de error no debería ser nulo");
        org.junit.jupiter.api.Assertions.assertTrue(
                resultado.contains("No se puede eliminar las categorias con ID"),
                "El mensaje debe indicar que las categorias no existen");
 
        verify(categoriasRepository, times(1)).findById(idInexistente);
    }

}
