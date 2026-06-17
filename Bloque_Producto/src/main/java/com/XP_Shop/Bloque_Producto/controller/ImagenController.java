package com.XP_Shop.Bloque_Producto.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.XP_Shop.Bloque_Producto.assemblers.ImagenModelAssemble;
import com.XP_Shop.Bloque_Producto.dto.ImagenDTO;
import com.XP_Shop.Bloque_Producto.model.Imagen;
import com.XP_Shop.Bloque_Producto.service.ImagenService;

@RestController
@RequestMapping("/api/v1/imagen")

public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    @Autowired
    private ImagenModelAssemble assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ImagenDTO>> todasLasImagen(){
        List<EntityModel<ImagenDTO>> imagen = imagenService.listarImagen().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(imagen,
            linkTo(methodOn(ImagenController.class).todasLasImagen()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenDTO> buscarPorId(Integer id){
        try {
            ImagenDTO imagen = imagenService.buscarImagenPorId(id);
            return new ResponseEntity<>(imagen, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Imagen> agregarImagen(@RequestBody Imagen imagen) {
        try {
            imagenService.guardarImagen(imagen);
            return new ResponseEntity<>(imagen, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Imagen> editarRegiom(@PathVariable Integer id, @RequestBody Imagen imagen) {
        try {
            imagenService.guardarImagen(imagen);
            return new ResponseEntity<>(imagen, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imagen> actualizarImagen(@PathVariable Integer id, @RequestBody Imagen imagen){
        try{
            imagenService.actualizarImagen(id, imagen);
            return new ResponseEntity<>(imagen, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminaImagen(@PathVariable Integer id) {
        try {
            imagenService.eliminarImagen(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
