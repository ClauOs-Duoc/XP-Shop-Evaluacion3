package com.XP_Shop.Bloque_Usuario.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Usuario.dto.UsuarioDTO;
import com.XP_Shop.Bloque_Usuario.model.Usuario;
import com.XP_Shop.Bloque_Usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private UsuarioDTO convertirUsuarioADTO(Usuario usuario){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setCorreo(usuario.getCorreo());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        if (usuario.getId_boletas() != null) {
            List<Integer> boletas = new ArrayList<>();
            for (Integer boletaId : usuario.getId_boletas()) {
                boletas.add(boletaId);
            }
            dto.setIdBoleta(boletas);
        }
        dto.setNombreComuna(usuario.getComuna().getNombreComuna());

        return dto;
    }

    public List<UsuarioDTO> ListarUsuario() {
        log.info("Listando todos los usuarios");
        return usuarioRepository.findAll().stream()
                    .map(this::convertirUsuarioADTO)
                    .toList();
    }
    
    public UsuarioDTO BuscarUsuarioPorId(Integer id) {
        log.info("Buscando usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Usuario con ID {} no encontrado", id);
                return new RuntimeException("Usuario no encontrado");
            });
        return convertirUsuarioADTO(usuario);
    }

    public UsuarioDTO guardarUsuario(Usuario usuario) {
        log.info("Guardando nuevo usuario: {}", usuario.getNombreUsuario());
        Usuario savedUsuario = usuarioRepository.save(usuario);
        log.info("Usuario guardado con ID: {}", savedUsuario.getIdUsuario());
        return convertirUsuarioADTO(savedUsuario);
    }

    public UsuarioDTO actualizarUsuario(Integer id, Usuario usuario) {
        log.info("Actualizando usuario con ID: {}", id);
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Usuario con ID {} no encontrado para actualizar", id);
                return new RuntimeException("El usuario no existe.");
            });
        if (usuario.getNombreUsuario() != null) {
            usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
        }
        if (usuario.getCorreo() != null) {
            usuarioExistente.setCorreo(usuario.getCorreo());
        }
        if (usuario.getFechaNacimiento() != null) {
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
        }
        if (usuario.getId_boletas() != null) {
            usuarioExistente.setId_boletas(usuario.getId_boletas());
        }
        if (usuario.getComuna() != null) {
            usuarioExistente.setComuna(usuario.getComuna());
        }

        Usuario updatedUsuario = usuarioRepository.save(usuarioExistente);
        log.info("Usuario con ID {} actualizado exitosamente", id);
        return convertirUsuarioADTO(updatedUsuario);
    }

    public Void EliminarUsuario(Integer id) {
        log.info("Eliminando usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Usuario con ID {} no encontrado para eliminar", id);
                return new RuntimeException("No se puede eliminar el usuario con ID " + id + " no existe.");
            });
        usuarioRepository.delete(usuario);
        log.info("Usuario con ID {} eliminado exitosamente", id);
        return null;
    }
    
}
