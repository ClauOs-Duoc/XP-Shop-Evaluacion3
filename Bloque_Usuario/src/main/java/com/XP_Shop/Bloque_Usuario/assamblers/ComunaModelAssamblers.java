package com.XP_Shop.Bloque_Usuario.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.XP_Shop.Bloque_Usuario.controller.ComunaController;
import com.XP_Shop.Bloque_Usuario.dto.ComunaDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ComunaModelAssamblers implements RepresentationModelAssembler<ComunaDTO, EntityModel<ComunaDTO>>{

    @Override
    public EntityModel<ComunaDTO> toModel(ComunaDTO comuna) {
        return EntityModel.of(comuna,
            linkTo(methodOn(ComunaController.class).buscarPorId(comuna.getIdComuna())).withSelfRel(),
            linkTo(methodOn(ComunaController.class).todasLasComunas()).withRel("comunas"));
    }
}
