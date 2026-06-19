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

import com.XP_Shop.Bloque_Producto.assemblers.CategoriaModelAssemble;
import com.XP_Shop.Bloque_Producto.dto.CategoriaDTO;
import com.XP_Shop.Bloque_Producto.model.Categoria;
import com.XP_Shop.Bloque_Producto.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/v1/categoria")
@Tag(name = "Categoria Controller", description = "Endpoint en la gestion de categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssemble assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Lista todas las  categoria", description = "Obtiene una lista de todas las categoria")
    public CollectionModel<EntityModel<CategoriaDTO>> todasLasCategoria(){
        List<EntityModel<CategoriaDTO>> categoria = categoriaService.listarCategoria().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(categoria,
            linkTo(methodOn(CategoriaController.class).todasLasCategoria()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> buscarPorId(Integer id){
        try {
            CategoriaDTO categoria = categoriaService.buscarCategoriaPorId(id);
            return ResponseEntity.ok(assembler.toModel(categoria));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> agregarCategoria(@RequestBody Categoria categoria) {
        try {
            CategoriaDTO newCategoria = categoriaService.guardarCategoria(categoria);
            return ResponseEntity
                .created(linkTo(methodOn(CategoriaController.class).buscarPorId(newCategoria.getIdCategoria())).toUri())
                .body(assembler.toModel(newCategoria));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        try{
            categoriaService.actualizarCategoria(id, categoria);
            CategoriaDTO categoriaUpdate = categoriaService.guardarCategoria(categoria);
            return ResponseEntity.ok(assembler.toModel(categoriaUpdate));
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> editarRegiom(@PathVariable Integer id, @RequestBody Categoria categoria) {
        try {
            CategoriaDTO categoriaPatch = categoriaService.guardarCategoria(categoria);
            return ResponseEntity.ok(assembler.toModel(categoriaPatch));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarCategoria(@PathVariable Integer id) {
        try {
            categoriaService.eliminarCategoria(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
