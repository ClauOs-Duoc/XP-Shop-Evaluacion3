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

import com.XP_Shop.Bloque_Producto.assemblers.CategoriasModelAssemble;
import com.XP_Shop.Bloque_Producto.model.Categorias;
import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.service.CategoriasService;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasService categoriasService;

    @Autowired
    private CategoriasModelAssemble assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Categorias>> todasLasCategorias(){
        List<EntityModel<Categorias>> categorias = categoriasService.listaCategorias().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(categorias,
            linkTo(methodOn(CategoriasController.class).todasLasCategorias()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Categorias>> buscarPorId(Integer id){
        try {
            Categorias categorias = categoriasService.buscarCategoriasPorId(id);
            return ResponseEntity.ok(assembler.toModel(categorias));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Categorias>> agregarCategorias(@RequestBody Categorias categorias) {
        try {
            Categorias newCategorias = categoriasService.guardarCategorias(categorias);
            return ResponseEntity
                .created(linkTo(methodOn(ProductoController.class).buscarPorId(newCategorias.getIdCategorias())).toUri())
                .body(assembler.toModel(newCategorias));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Categorias>> editarCategoria(@PathVariable Integer id, @RequestBody Marcas marcas) {
        try {
            marcasService.guardarMarcas(marcas);
            Marcas marcasPatch = marcas;
            return ResponseEntity.ok(assembler.toModel(marcasPatch));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marcas>> actualizarMarcas(@PathVariable Integer id, @RequestBody Marcas marcas) {
        try{
            marcasService.actualizarMarcas(id, marcas);
            Marcas marcasUpdate = marcasService.guardarMarcas(marcas);
            return ResponseEntity.ok(assembler.toModel(marcasUpdate));
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategorias(@PathVariable Integer id) {
        try {
            categoriasService.eliminarCategorias(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
