package com.XP_Shop.Bloque_Boleta.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.XP_Shop.Bloque_Boleta.dto.MetodoEnvioDTO;

@Component
public class MetodoEnvioModelAssambler implements RepresentationModelAssembler<MetodoEnvioDTO, EntityModel<MetodoEnvioDTO>>{

    @Override
    public EntityModel<MetodoEnvioDTO> toModel(MetodoEnvioDTO metodoEnvio){
        return EntityModel.of(metodoEnvio);
    }

}
