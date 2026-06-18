package com.XP_Shop.Bloque_Boleta.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.XP_Shop.Bloque_Boleta.controller.BoletaController;
import com.XP_Shop.Bloque_Boleta.dto.BoletaDTO;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BoletaModelAssambler implements RepresentationModelAssembler<BoletaDTO, EntityModel<BoletaDTO>>{

    @Override
    public EntityModel<BoletaDTO> toModel(BoletaDTO boleta){
        return EntityModel.of(boleta, 
            linkTo(methodOn(BoletaController.class).buscarPorId(boleta.getIdBoleta())).withSelfRel(), 
            linkTo(methodOn(BoletaController.class).listarBoletas()).withRel("Boletas"));
    }

}
