package com.XP_Shop.Bloque_Producto.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.XP_Shop.Bloque_Producto.controller.MarcaController;
import com.XP_Shop.Bloque_Producto.dto.MarcaDTO;

@Component
public class MarcaModelAssemble implements RepresentationModelAssembler<MarcaDTO, EntityModel<MarcaDTO>>{

    @Override
    public EntityModel<MarcaDTO> toModel(MarcaDTO marca){
        return EntityModel.of(marca, 
            linkTo(methodOn(MarcaController.class).buscarPorId(marca.getIdMarca())).withSelfRel(), 
            linkTo(methodOn(MarcaController.class).todasLasMarca()).withRel("Marca"));
    }

}
