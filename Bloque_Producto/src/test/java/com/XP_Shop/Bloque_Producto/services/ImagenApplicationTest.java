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
 
import com.XP_Shop.Bloque_Producto.dto.ImagenDTO;
import com.XP_Shop.Bloque_Producto.model.Imagen;
import com.XP_Shop.Bloque_Producto.model.Producto;
import com.XP_Shop.Bloque_Producto.repository.ImagenRepository;
import com.XP_Shop.Bloque_Producto.service.ImagenService;

import net.datafaker.Faker;
 
@ExtendWith(MockitoExtension.class)
public class ImagenApplicationTest {

    @Mock
    private ImagenRepository imagenRepository;
 
    @InjectMocks
    private ImagenService imagenService;
 
    private Faker faker = new Faker();
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    private Imagen crearImagenFalsa(Integer id) {
        Imagen imagen = new Imagen();
        imagen.setIdImagen(id);
        imagen.setNombreImagen(faker.animal().name());
        imagen.setProducto(new Producto());
        return imagen;
    }
 
    @Test
    void testListarImagenExitoso() {
        Imagen imagen1 = crearImagenFalsa(0);
        Imagen imagen2 = crearImagenFalsa(1);
        Imagen imagen3 = crearImagenFalsa(2);
 
        when(imagenRepository.findAll()).thenReturn(List.of(imagen1, imagen2, imagen3));
 
        List<ImagenDTO> resultado = imagenService.listarImagen();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(3, resultado.size(), "Deben retornarse 3 imágenes");
        assertEquals(imagen1.getNombreImagen(), resultado.get(0).getNombreImagen(),
                "El nombre de la primera imagen debe coincidir");
 
        verify(imagenRepository, times(1)).findAll();
    }
 
    @Test
    void testBuscarImagenPorIdExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Imagen imagenFalsa = crearImagenFalsa(id);
 
        when(imagenRepository.findById(id)).thenReturn(Optional.of(imagenFalsa));

        ImagenDTO resultado = imagenService.buscarImagenPorId(id);

        assertNotNull(resultado, "El DTO no debería ser nulo");
        assertEquals(id, resultado.getIdImagen(),
                "El ID del DTO debe coincidir con el buscado");
        assertEquals(imagenFalsa.getNombreImagen(), resultado.getNombreImagen(),
                "El nombre transformado al DTO debe coincidir con el de la base de datos");
 
        verify(imagenRepository, times(1)).findById(id);
    }
 
    @Test
    void testBuscarImagenPorIdNoEncontrado() {
        Integer id = 777;
 
        when(imagenRepository.findById(id)).thenReturn(Optional.empty());
 
        // WHEN & THEN
        assertThrows(RuntimeException.class,
                () -> imagenService.buscarImagenPorId(id),
                "Debe debe mostrarse una exepcion cuando la imagen no existe");
 
        verify(imagenRepository, times(1)).findById(id);
    }
 
    @Test
    void testGuardarImagenExitoso() {
        Imagen imagenEntrada = crearImagenFalsa(null);
        Imagen imagenGuardada = crearImagenFalsa(10);
        imagenGuardada.setNombreImagen(imagenEntrada.getNombreImagen());
 
        when(imagenRepository.save(imagenEntrada)).thenReturn(imagenGuardada);
 
        ImagenDTO resultado = imagenService.guardarImagen(imagenEntrada);
 
        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(imagenGuardada.getIdImagen(), resultado.getIdImagen(),
                "El ID asignado por la base de datos debe reflejarse en el DTO");
        assertEquals(imagenEntrada.getNombreImagen(), resultado.getNombreImagen(),
                "El nombre guardado debe coincidir con el ingresado");
 
        verify(imagenRepository, times(1)).save(imagenEntrada);
    }
 
    @Test
    void testActualizarImagenExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Imagen imagenExistente = crearImagenFalsa(id);
 
        Imagen datosNuevos = new Imagen();
        datosNuevos.setNombreImagen(faker.animal().name());
        datosNuevos.setProducto(new Producto());
 
        Imagen imagenActualizada = new Imagen();
        imagenActualizada.setIdImagen(id);
        imagenActualizada.setNombreImagen(datosNuevos.getNombreImagen());
        imagenActualizada.setProducto(datosNuevos.getProducto());
 
        when(imagenRepository.findById(id)).thenReturn(Optional.of(imagenExistente));
        when(imagenRepository.save(imagenExistente)).thenReturn(imagenActualizada);
 
        ImagenDTO resultado = imagenService.actualizarImagen(id, datosNuevos);
 
        assertNotNull(resultado, "El DTO actualizado no deberia ser nulo");
        assertEquals(datosNuevos.getNombreImagen(), resultado.getNombreImagen(),
                "El nombre actualizado debe coincidir con los datos enviados");
 
        verify(imagenRepository, times(1)).findById(id);
        verify(imagenRepository, times(1)).save(imagenExistente);
    }
 
    @Test
    void testActualizarImagenNoEncontrado() {
        Integer id = 66;
        Imagen datosNuevos = crearImagenFalsa(null);
 
        when(imagenRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> imagenService.actualizarImagen(id, datosNuevos),
                "Debe mostrarse una exepcion cuando la imagen a actualizar no existe");
 
        verify(imagenRepository, times(1)).findById(id);
    }
 
    @Test
    void testEliminarImagenExitoso() {
        Integer id = faker.number().numberBetween(1, 100);
        Imagen imagenFalsa = crearImagenFalsa(id);
 
        when(imagenRepository.findById(id)).thenReturn(Optional.of(imagenFalsa));
 
        Void resultado = imagenService.eliminarImagen(id);
 
        assertEquals(null, resultado, "Eliminar imagen debe retornar null (Void)");
 
        verify(imagenRepository, times(1)).findById(id);
        verify(imagenRepository, times(1)).delete(imagenFalsa);
    }
 
    @Test
    void testEliminarImagenNoEncontrado() {
        Integer id = 69;
 
        when(imagenRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
                () -> imagenService.eliminarImagen(id),
                "Debe lanzar RuntimeException cuando la imagen a eliminar no existe");
 
        verify(imagenRepository, times(1)).findById(id);
    }

}
