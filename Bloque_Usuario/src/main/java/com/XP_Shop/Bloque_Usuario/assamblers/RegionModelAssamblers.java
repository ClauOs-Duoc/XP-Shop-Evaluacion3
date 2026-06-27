package com.XP_Shop.Bloque_Usuario.assamblers;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.XP_Shop.Bloque_Usuario.controller.RegionController;
import com.XP_Shop.Bloque_Usuario.dto.RegionDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RegionModelAssamblers implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>>{

    @Override
     public EntityModel<RegionDTO> toModel(RegionDTO region) {
        return EntityModel.of(region,
            linkTo(methodOn(RegionController.class).buscarPorId(region.getIdRegion())).withSelfRel(),
            linkTo(methodOn(RegionController.class).todasLasRegiones()).withRel("region"));
    }
}
