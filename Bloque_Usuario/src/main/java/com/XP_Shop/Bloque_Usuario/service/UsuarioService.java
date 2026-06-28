package com.XP_Shop.Bloque_Usuario.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Usuario.model.Usuario;
import com.XP_Shop.Bloque_Usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listaUsuario() {
        log.info("Listando todos los usuarios");
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        log.info("Buscando usuario por ID: {}", id);
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El usuario no existe."));
    }

    public Usuario guardarUsuario(Usuario usuario) {
        log.info("Guardando nuevo usuario");
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuario) {
        log.info("Actualizando usuario con ID: {}", id);
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El usuario no existe."));

        if (usuario.getNombreUsuario() != null) {
            usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
        }
        if (usuario.getCorreo() != null) {
            usuarioExistente.setCorreo(usuario.getCorreo());
        }
        if (usuario.getFechaNacimiento() != null) {
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
        }
        if (usuario.getComuna() != null) {
            usuarioExistente.setComuna(usuario.getComuna());
        }

        log.info("Usuario actualizado con ID: {}", id);
        return usuarioRepository.save(usuarioExistente);
    }

    public String eliminarUsuario(Integer id) {
        log.info("Eliminando usuario con ID: {}", id);
        try {
            Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar el usuario con ID " + id + " no existe."));
            usuarioRepository.delete(usuario);
            log.info("Usuario eliminado con ID: {}", id);
            return "El usuario ha sido eliminado correctamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
}

