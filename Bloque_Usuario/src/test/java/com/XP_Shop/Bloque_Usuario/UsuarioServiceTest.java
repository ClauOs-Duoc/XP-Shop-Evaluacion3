package com.XP_Shop.Bloque_Usuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.XP_Shop.Bloque_Usuario.model.Comuna;
import com.XP_Shop.Bloque_Usuario.model.Usuario;
import com.XP_Shop.Bloque_Usuario.repository.UsuarioRepository;
import com.XP_Shop.Bloque_Usuario.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {


    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;
    private Comuna comunaMock;

    @BeforeEach
    void setUp() {
        comunaMock = new Comuna();
        comunaMock.setIdComuna(1);
        comunaMock.setNombreComuna("Santiago");

        usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1);
        usuarioMock.setNombreUsuario("Tomas123");
        usuarioMock.setCorreo("Tomas@test.com");
        usuarioMock.setFechaNacimiento(LocalDate.of(1995, 5, 15));
        usuarioMock.setComuna(comunaMock);
    }       

    @Test
    void guardarUsuario_exito() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        var resultado = usuarioService.guardarUsuario(usuarioMock);

        assertNotNull(resultado);
        assertEquals("Tomas123", resultado.getNombreUsuario());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void buscarUsuarioPorId_exito() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioMock));

        var resultado = usuarioService.buscarUsuarioPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdUsuario());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void buscarUsuarioPorId_error() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarUsuarioPorId(99);
        });

        assertEquals("El usuario no existe.", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(99);
    }

    @Test
    void eliminarUsuario_exito() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioMock));
        doNothing().when(usuarioRepository).delete(any(Usuario.class));

        var resultado = usuarioService.eliminarUsuario(1);

        assertEquals("El usuario ha sido eliminado correctamente.", resultado);
        verify(usuarioRepository, times(1)).findById(1);
        verify(usuarioRepository, times(1)).delete(any(Usuario.class));
    }
}

