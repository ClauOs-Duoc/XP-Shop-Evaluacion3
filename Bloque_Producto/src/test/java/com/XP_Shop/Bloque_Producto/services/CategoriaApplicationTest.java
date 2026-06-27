package com.XP_Shop.Bloque_Producto.services;

import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.XP_Shop.Bloque_Producto.dto.CategoriaDTO;
import com.XP_Shop.Bloque_Producto.model.Categoria;
import com.XP_Shop.Bloque_Producto.model.Categorias;
import com.XP_Shop.Bloque_Producto.repository.CategoriaRepository;
import com.XP_Shop.Bloque_Producto.service.CategoriaService;

import net.datafaker.Faker;
 
@ExtendWith(MockitoExtension.class)
public class CategoriaApplicationTest {

    @Mock
    private CategoriaRepository categoriaRepository;
 
    @InjectMocks
    private CategoriaService categoriaService;
 
    private Faker faker = new Faker();
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Categoria crearCategoriaFalsa(Integer id) {
        Categorias categorias = new Categorias();
        categorias.setIdCategorias(1);
        categorias.setCategoria(null);
        categorias.setProducto(null);

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(id);
        categoria.setNombreCategoria(faker.company().name());
        categoria.setCategorias(categorias);
        return categoria;
    }
 
    @Test
    void testListarCategoriaExitoso() {
        Categoria categoria1 = crearCategoriaFalsa(1);
        Categoria categoria2 = crearCategoriaFalsa(2);
        Categoria categoria3 = crearCategoriaFalsa(3);
 
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria1, categoria2, categoria3));
 
        List<CategoriaDTO> resultado = categoriaService.listarCategoria();
 
        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(3, resultado.size(), "Deben retornarse 3 categorias");
        assertEquals(categoria1.getNombreCategoria(), resultado.get(0).getNombreCategoria(),
                "El nombre de la primera categoria debe coincidir");
 
        verify(categoriaRepository, times(1)).findAll();
    }
 
    @Test
    void testBuscarCategoriaPorIdExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Categoria categoriaFalsa = crearCategoriaFalsa(id);
 
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaFalsa));
 
        CategoriaDTO resultado = categoriaService.buscarCategoriaPorId(id);
 
        assertNotNull(resultado, "El DTO no debería ser nulo");
        assertEquals(id, resultado.getIdCategoria(),
                "El ID del DTO debe coincidir con el buscado");
        assertEquals(categoriaFalsa.getNombreCategoria(), resultado.getNombreCategoria(),
                "El nombre transformado al DTO debe coincidir con el de la base de datos");
 
        verify(categoriaRepository, times(1)).findById(id);
    }
 
    @Test
    void testBuscarCategoriaPorIdNoEncontrado() {
        Integer id = 82;
 
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> categoriaService.buscarCategoriaPorId(id),
                "Debe mostrarse la exepcion cuando la categoria no existe");
 
        verify(categoriaRepository, times(1)).findById(id);
    }
 
    @Test
    void testGuardarCategoriaExitoso() {
        Categoria categoriaEntrada = crearCategoriaFalsa(null);
        Categoria categoriaGuardada = crearCategoriaFalsa(10);
        categoriaGuardada.setNombreCategoria(categoriaEntrada.getNombreCategoria());
 
        when(categoriaRepository.save(categoriaEntrada)).thenReturn(categoriaGuardada);
 
        CategoriaDTO resultado = categoriaService.guardarCategoria(categoriaEntrada);
 
        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(categoriaGuardada.getIdCategoria(), resultado.getIdCategoria(),
                "El ID asignado por la base de datos debe reflejarse en el DTO");
        assertEquals(categoriaEntrada.getNombreCategoria(), resultado.getNombreCategoria(),
                "El nombre guardado debe coincidir con el ingresado");
 
        verify(categoriaRepository, times(1)).save(categoriaEntrada);
    }
 
    @Test
    void testActualizarCategoriaExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Categoria categoriaExistente = crearCategoriaFalsa(id);

        Categoria datosNuevos = new Categoria();
        datosNuevos.setNombreCategoria(faker.company().name());

        Categorias categorias = new Categorias();
        categorias.setIdCategorias(1);
        categorias.setCategoria(null);
        categorias.setProducto(null);

        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setIdCategoria(id);
        categoriaActualizada.setNombreCategoria(datosNuevos.getNombreCategoria());
        categoriaActualizada.setCategorias(categorias);

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.save(categoriaExistente)).thenReturn(categoriaActualizada);

        CategoriaDTO resultado = categoriaService.actualizarCategoria(id, datosNuevos);

        assertNotNull(resultado, "El DTO actualizado no debería ser nulo");
        assertEquals(datosNuevos.getNombreCategoria(), resultado.getNombreCategoria(),
            "El nombre actualizado debe coincidir con los datos enviados");

        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).save(categoriaExistente);
    }
 
    @Test
    void testActualizarCategoriaNoEncontrado() {
        Integer id = 169;
        Categoria datosNuevos = crearCategoriaFalsa(null);
 
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> categoriaService.actualizarCategoria(id, datosNuevos),
                "Debe mostrase la exepcion cuando la categoria a actualizar no existe");
 
        verify(categoriaRepository, times(1)).findById(id);
    }
 
    @Test
    void testEliminarCategoriaExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Categoria categoriaFalsa = crearCategoriaFalsa(id);
 
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaFalsa));
 
        Void resultado = categoriaService.eliminarCategoria(id);
 
        assertEquals(null, resultado, "Eliminar categoria debe retornar null (Void)");
 
        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).delete(categoriaFalsa);
    }
 
    @Test
    void testEliminarCategoriaNoEncontrado() {
        Integer id = 999;
 
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> categoriaService.eliminarCategoria(id),
                "Debe lanzar RuntimeException cuando la categoría a eliminar no existe");
 
        verify(categoriaRepository, times(1)).findById(id);
    }

}