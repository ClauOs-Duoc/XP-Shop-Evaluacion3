package com.XP_Shop.Bloque_Producto.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
 
import com.XP_Shop.Bloque_Producto.dto.ProductoDTO;
import com.XP_Shop.Bloque_Producto.model.Categorias;
import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.model.Producto;
import com.XP_Shop.Bloque_Producto.model.Productos;
import com.XP_Shop.Bloque_Producto.repository.ProductoRepository;
import com.XP_Shop.Bloque_Producto.service.ProductoService;

import net.datafaker.Faker;
 
@ExtendWith(MockitoExtension.class)
public class ProductoApplicationTest {

    @Mock
    private ProductoRepository productoRepository;
 
    @InjectMocks
    private ProductoService productoService;
 
    private Faker faker = new Faker();
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    private Producto crearProductoFalso(Integer id) {
        Marcas marcas = new Marcas();
        marcas.setIdMarcas(1);
        marcas.setMarcas(null);
        marcas.setProductos(null);
 
        Categorias categorias = new Categorias();
        categorias.setIdCategorias(1);
        categorias.setCategoria(null);
        categorias.setProducto(null);
 
        Productos productos = new Productos();
        productos.setIdProductos(1);
 
        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombreProducto(faker.book().title());
        producto.setPrecio(342859.0);
        producto.setDescripcionProducto(faker.famousLastWords().lastWords());
        producto.setStock(631);
        producto.setMarcas(marcas);
        producto.setCategorias(categorias);
        producto.setProductos(productos);
        producto.setImagenes(new ArrayList<>());
 
        return producto;
    }
 
    @Test
    void testListarProductoExitoso() {
        Producto producto1 = crearProductoFalso(0);
        Producto producto2 = crearProductoFalso(1);
        Producto producto3 = crearProductoFalso(2);
 
        when(productoRepository.findAll()).thenReturn(List.of(producto1, producto2, producto3));
 
        List<ProductoDTO> resultado = productoService.listarProducto();
 
        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(3, resultado.size(), "Deben retornarse 3 productos");
        assertEquals(producto1.getNombreProducto(), resultado.get(0).getNombreProducto(),
                "El nombre del primer producto debe coincidir");
 
        verify(productoRepository, times(1)).findAll();
    }
 
    @Test
    void testBuscarProductoPorIdExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Producto productoFalso = crearProductoFalso(id);
 
        when(productoRepository.findById(id)).thenReturn(Optional.of(productoFalso));
 
        ProductoDTO resultado = productoService.buscarProductoPorId(id);
 
        assertNotNull(resultado, "El DTO no deberia ser nulo");
        assertEquals(id, resultado.getIdProducto(),
                "El ID del DTO debe coincidir con el buscado");
        assertEquals(productoFalso.getNombreProducto(), resultado.getNombreProducto(),
                "El nombre transformado al DTO debe coincidir con el de la base de datos");
        assertEquals(productoFalso.getPrecio(), resultado.getPrecio(),
                "El precio transformado al DTO debe coincidir con el de la base de datos");
 
        verify(productoRepository, times(1)).findById(id);
    }
 
    @Test
    void testBuscarProductoPorIdNoEncontrado() {
        Integer id = 820;
 
        when(productoRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> productoService.buscarProductoPorId(id),
                "Debe mostrarse la exepcion cuando el producto no existe");
 
        verify(productoRepository, times(1)).findById(id);
    }
 
    @Test
    void testGuardarProductoExitoso() {
        Producto productoEntrada = crearProductoFalso(null);
        Producto productoGuardado = crearProductoFalso(10);
        productoGuardado.setNombreProducto(productoEntrada.getNombreProducto());
        productoGuardado.setPrecio(productoEntrada.getPrecio());
 
        when(productoRepository.save(productoEntrada)).thenReturn(productoGuardado);
 
        ProductoDTO resultado = productoService.guardarProducto(productoEntrada);
 
        assertNotNull(resultado, "El DTO guardado no debería ser nulo");
        assertEquals(productoGuardado.getIdProducto(), resultado.getIdProducto(),
                "El ID asignado por la DB debe reflejarse en el DTO");
        assertEquals(productoEntrada.getNombreProducto(), resultado.getNombreProducto(),
                "El nombre guardado debe coincidir con el ingresado");
 
        verify(productoRepository, times(1)).save(productoEntrada);
    }
 
    @Test
    void testActualizarProductoExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Producto productoExistente = crearProductoFalso(id);
 
        Producto datosNuevos = new Producto();
        datosNuevos.setNombreProducto(faker.book().title());
        datosNuevos.setPrecio(199999.0);
        datosNuevos.setDescripcionProducto(faker.famousLastWords().lastWords());
        datosNuevos.setStock(50);
        datosNuevos.setMarcas(productoExistente.getMarcas());
        datosNuevos.setCategorias(productoExistente.getCategorias());
        datosNuevos.setProductos(productoExistente.getProductos());
        datosNuevos.setImagenes(new ArrayList<>());
 
        Producto productoActualizado = crearProductoFalso(id);
        productoActualizado.setNombreProducto(datosNuevos.getNombreProducto());
        productoActualizado.setPrecio(datosNuevos.getPrecio());
 
        when(productoRepository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(productoExistente)).thenReturn(productoActualizado);
 
        ProductoDTO resultado = productoService.actualizarProducto(id, datosNuevos);
 
        assertNotNull(resultado, "El DTO actualizado no deberia ser nulo");
        assertEquals(datosNuevos.getNombreProducto(), resultado.getNombreProducto(),
                "El nombre actualizado debe coincidir con los datos enviados");
        assertEquals(datosNuevos.getPrecio(), resultado.getPrecio(),
                "El precio actualizado debe coincidir con los datos enviados");
 
        verify(productoRepository, times(1)).findById(id);
        verify(productoRepository, times(1)).save(productoExistente);
    }
 
    @Test
    void testActualizarProductoNoEncontrado() {
        Integer id = 823;
        Producto datosNuevos = crearProductoFalso(null);
 
        when(productoRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> productoService.actualizarProducto(id, datosNuevos),
                "Debe mostrarse la exepcion cuando el producto a actualizar no existe");
 
        verify(productoRepository, times(1)).findById(id);
    }
 
    @Test
    void testEliminarProductoExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Producto productoFalso = crearProductoFalso(id);
 
        when(productoRepository.findById(id)).thenReturn(Optional.of(productoFalso));
 
        Void resultado = productoService.eliminarProducto(id);
 
        assertEquals(null, resultado, "Eliminar producto debe retornar null (Void)");
 
        verify(productoRepository, times(1)).findById(id);
        verify(productoRepository, times(1)).delete(productoFalso);
    }
 
    @Test
    void testEliminarProductoNoEncontrado() {
        Integer id = 836;
 
        when(productoRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> productoService.eliminarProducto(id),
                "Debe mostrarse la exepcion cuando el producto a eliminar no existe");
 
        verify(productoRepository, times(1)).findById(id);
    }

}
