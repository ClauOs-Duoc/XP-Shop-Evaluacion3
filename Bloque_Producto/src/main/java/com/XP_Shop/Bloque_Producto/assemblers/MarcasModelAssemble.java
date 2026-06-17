package com.XP_Shop.Bloque_Producto.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.XP_Shop.Bloque_Producto.controller.MarcasController;
import com.XP_Shop.Bloque_Producto.model.Marcas;

@Component
public class MarcasModelAssemble implements RepresentationModelAssembler<Marcas, EntityModel<Marcas>>{

    @Override
    public EntityModel<Marcas> toModel(Marcas marcas){
        return EntityModel.of(marcas, 
            linkTo(methodOn(MarcasController.class).buscarPorId(marcas.getIdMarcas())).withSelfRel(), 
            linkTo(methodOn(MarcasController.class).todasLasMarcas()).withRel("Marcas"));
    }

}
