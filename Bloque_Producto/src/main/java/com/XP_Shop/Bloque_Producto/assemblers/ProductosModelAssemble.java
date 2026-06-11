package com.XP_Shop.Bloque_Producto.assemblers;

import com.XP_Shop.Bloque_Producto.controller.ProductosController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.XP_Shop.Bloque_Producto.model.Productos;


@Component
public class ProductosModelAssemble implements RepresentationModelAssembler<Productos, EntityModel<Productos>>{

    @Override
    public EntityModel<Productos> toModel(Productos productos){
        return EntityModel.of(productos, 
            linkTo(methodOn(ProductosController.class).buscarPorId(productos.getIdProductos())).withSelfRel(), 
            linkTo(methodOn(ProductosController.class).todosLosProductos()).withRel("Productos"));
    }

}
