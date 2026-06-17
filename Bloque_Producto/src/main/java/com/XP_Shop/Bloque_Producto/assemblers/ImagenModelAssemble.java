package com.XP_Shop.Bloque_Producto.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.XP_Shop.Bloque_Producto.controller.ImagenController;
import com.XP_Shop.Bloque_Producto.dto.ImagenDTO;

@Component
public class ImagenModelAssemble implements RepresentationModelAssembler<ImagenDTO, EntityModel<ImagenDTO>>{

    @Override
    public EntityModel<ImagenDTO> toModel(ImagenDTO imagen){
        return EntityModel.of(imagen, 
            linkTo(methodOn(ImagenController.class).buscarPorId(imagen.getIdImagen())).withSelfRel(), 
            linkTo(methodOn(ImagenController.class).todasLasImagen()).withRel("Imagen"));
    }

}
