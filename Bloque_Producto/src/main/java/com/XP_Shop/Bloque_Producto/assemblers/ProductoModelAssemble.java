package com.XP_Shop.Bloque_Producto.assemblers;

import com.XP_Shop.Bloque_Producto.controller.ProductoController;
import com.XP_Shop.Bloque_Producto.dto.ProductoDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductoModelAssemble implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>>{

    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO producto){
        return EntityModel.of(producto, 
            linkTo(methodOn(ProductoController.class).buscarPorId(producto.getIdProducto())).withSelfRel(), 
            linkTo(methodOn(ProductoController.class).todosLosProducto()).withRel("Producto"));
    }

}
