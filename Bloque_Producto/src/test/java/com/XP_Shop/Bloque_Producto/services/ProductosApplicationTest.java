package com.XP_Shop.Bloque_Producto.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
 
import com.XP_Shop.Bloque_Producto.model.Producto;
import com.XP_Shop.Bloque_Producto.model.Productos;
import com.XP_Shop.Bloque_Producto.repository.ProductosRepository;
import com.XP_Shop.Bloque_Producto.service.ProductosService;
 
@ExtendWith(MockitoExtension.class)
public class ProductosApplicationTest {

    @Mock
    private ProductosRepository productosRepository;
 
    @InjectMocks
    private ProductosService productosService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    private Productos crearProductosFalso(Integer id) {
        Productos productos = new Productos();
        productos.setIdProductos(id);
        productos.setProducto(new ArrayList<>());
        productos.setId_detalleBoletas(id);
        return productos;
    }
 
    @Test
    void testListaProductosExitoso() {
        Productos productos1 = crearProductosFalso(0);
        Productos productos2 = crearProductosFalso(1);
        Productos productos3 = crearProductosFalso(2);
 
        when(productosRepository.findAll()).thenReturn(List.of(productos1, productos2, productos3));
 
        List<Productos> resultado = productosService.listaProductos();
 
        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(3, resultado.size(), "Deben retornarse 3 registros de productos");
        assertEquals(productos1.getIdProductos(), resultado.get(0).getIdProductos(),
                "El ID del primer elemento debe coincidir");
 
        verify(productosRepository, times(1)).findAll();
    }

    @Test
    void testBuscarProductosPorIdExitoso() {
        Integer id = 1;
        Productos productosFalso = crearProductosFalso(id);
 
        when(productosRepository.findById(id)).thenReturn(Optional.of(productosFalso));
 
        Productos resultado = productosService.buscarProductosPorId(id);

        assertNotNull(resultado, "El resultado no deberia ser nulo");
        assertEquals(id, resultado.getIdProductos(),
                "El ID retornado debe coincidir con el buscado");
 
        verify(productosRepository, times(1)).findById(id);
    }
 
    @Test
    void testBuscarProductosPorIdNoEncontrado() {
        Integer id = 912;
 
        when(productosRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> productosService.buscarProductosPorId(id),
                "Debe mostrarse la exepcion cuando los productos no existen");
 
        verify(productosRepository, times(1)).findById(id);
    }
 
    @Test
    void testGuardarProductosExitoso() {
        Productos productosEntrada = crearProductosFalso(null);
        Productos productosGuardado = crearProductosFalso(10);
 
        when(productosRepository.save(productosEntrada)).thenReturn(productosGuardado);
 
        Productos resultado = productosService.guardarProductos(productosEntrada);
 
        assertNotNull(resultado, "El resultado guardado no deberia ser nulo");
        assertEquals(10, resultado.getIdProductos(),
                "El ID asignado por la base de datos debe reflejarse en el resultado");
 
        verify(productosRepository, times(1)).save(productosEntrada);
    }
 
    @Test
    void testActualizarProductosExitoso() {
        Integer id = 1;
        Productos productosExistente = crearProductosFalso(id);
 
        Productos datosNuevos = new Productos();
        List<Producto> nuevosProductos = new ArrayList<>();
        datosNuevos.setProducto(nuevosProductos);
        datosNuevos.setId_detalleBoletas(id);
 
        Productos productosActualizado = crearProductosFalso(id);
 
        when(productosRepository.findById(id)).thenReturn(Optional.of(productosExistente));
        when(productosRepository.save(productosExistente)).thenReturn(productosActualizado);
 
        Productos resultado = productosService.actualizarProductos(id, datosNuevos);
 
        assertNotNull(resultado, "El resultado actualizado no debería ser nulo");
        assertEquals(id, resultado.getIdProductos(),
                "El ID debe mantenerse igual tras la actualizacion");
 
        verify(productosRepository, times(1)).findById(id);
        verify(productosRepository, times(1)).save(productosExistente);
    }
 
    @Test
    void testActualizarProductosNoEncontrado() {
        Integer id = 734;
        Productos datosNuevos = crearProductosFalso(null);
 
        when(productosRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> productosService.actualizarProductos(id, datosNuevos),
                "Debe mostrarse la exepcion cuando los productos a actualizar no existen");
 
        verify(productosRepository, times(1)).findById(id);
    }
 
    @Test
    void testEliminarProductosExitoso() {
        Integer id = 1;
        Productos productosFalso = crearProductosFalso(id);
 
        when(productosRepository.findById(id)).thenReturn(Optional.of(productosFalso));
 
        String resultado = productosService.eliminarProductos(id);
 
        assertEquals("Los productos han sido eliminados correctamente.", resultado,
                "El mensaje de exito debe coincidir con el definido en el service");
 
        verify(productosRepository, times(1)).findById(id);
        verify(productosRepository, times(1)).delete(productosFalso);
    }
 
    @Test
    void testEliminarProductosNoEncontrado() {
        Integer id = 734;
 
        when(productosRepository.findById(id)).thenReturn(Optional.empty());
 
        String resultado = productosService.eliminarProductos(id);
 
        assertNotNull(resultado, "El mensaje de error no debería ser nulo");
        assertTrue(resultado.contains("No se puede eliminar los productos con ID"),
                "El mensaje debe indicar que los productos no existen");
 
        verify(productosRepository, times(1)).findById(id);
    }

}
