package com.XP_Shop.Bloque_Boleta.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.XP_Shop.Bloque_Boleta.dto.MetodoPagoDTO;

@Component
public class MetodoPagoModelAssambler implements RepresentationModelAssembler<MetodoPagoDTO, EntityModel<MetodoPagoDTO>>{

    @Override
    public EntityModel<MetodoPagoDTO> toModel(MetodoPagoDTO metodoPago){
        return EntityModel.of(metodoPago);
    }

}
