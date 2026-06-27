package com.XP_Shop.Bloque_Usuario.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.XP_Shop.Bloque_Usuario.controller.UsuarioController;
import com.XP_Shop.Bloque_Usuario.dto.UsuarioDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class UsuarioAssamblers implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>>{
    @Override
     public EntityModel<UsuarioDTO> toModel(UsuarioDTO usuario) {
        return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioController.class).buscarPorId(usuario.getIdUsuario())).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).todosLosUsuarios()).withRel("usuario"));
    }
}
