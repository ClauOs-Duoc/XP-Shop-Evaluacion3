package com.XP_Shop.Bloque_Producto.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.XP_Shop.Bloque_Producto.controller.CategoriasController;
import com.XP_Shop.Bloque_Producto.model.Categorias;

@Component
public class CategoriasModelAssemble implements RepresentationModelAssembler<Categorias, EntityModel<Categorias>>{

    @Override
    public EntityModel<Categorias> toModel(Categorias categorias){
        return EntityModel.of(categorias, 
            linkTo(methodOn(CategoriasController.class).buscarPorId(categorias.getIdCategorias())).withSelfRel(), 
            linkTo(methodOn(CategoriasController.class).todasLasCategorias()).withRel("Categorias"));
    }

}
