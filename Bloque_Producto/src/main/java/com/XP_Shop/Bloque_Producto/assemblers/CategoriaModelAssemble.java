package com.XP_Shop.Bloque_Producto.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.XP_Shop.Bloque_Producto.controller.CategoriaController;
import com.XP_Shop.Bloque_Producto.dto.CategoriaDTO;

@Component
public class CategoriaModelAssemble implements RepresentationModelAssembler<CategoriaDTO, EntityModel<CategoriaDTO>>{

    @Override
    public EntityModel<CategoriaDTO> toModel(CategoriaDTO categoria){
        return EntityModel.of(categoria, 
            linkTo(methodOn(CategoriaController.class).buscarPorId(categoria.getIdCategoria())).withSelfRel(), 
            linkTo(methodOn(CategoriaController.class).todasLaCategoria()).withRel("Categoria"));
    }

}
