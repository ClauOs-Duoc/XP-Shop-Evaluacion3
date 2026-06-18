package com.XP_Shop.Bloque_Boleta.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.XP_Shop.Bloque_Boleta.dto.DetalleBoletaDTO;
import com.XP_Shop.Bloque_Boleta.controller.DetalleBoletaController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DetalleBoletaModelAssambler implements RepresentationModelAssembler<DetalleBoletaDTO, EntityModel<DetalleBoletaDTO>>{

    @Override
    public EntityModel<DetalleBoletaDTO> toModel(DetalleBoletaDTO detalleBoleta){
        return EntityModel.of(detalleBoleta, 
            linkTo(methodOn(DetalleBoletaController.class).buscarPorId(detalleBoleta.getIdDetalleBoleta())).withSelfRel(), 
            linkTo(methodOn(DetalleBoletaController.class).todosLosDetalleBoleta()).withRel("Detalles"));
    }



}
