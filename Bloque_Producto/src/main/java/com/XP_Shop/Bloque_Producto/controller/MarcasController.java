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

import com.XP_Shop.Bloque_Producto.assemblers.MarcasModelAssemble;
import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.service.MarcasService;

@RestController
@RequestMapping("/api/v1/marcas")
public class MarcasController {

    @Autowired
    private MarcasService marcasService;

    @Autowired
    private MarcasModelAssemble assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Marcas>> todasLasMarcas(){
        List<EntityModel<Marcas>> marcas = marcasService.listaMarcas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(marcas,
            linkTo(methodOn(MarcasController.class).todasLasMarcas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marcas>> buscarPorId(Integer id){
        try {
            Marcas marcas = marcasService.buscarMarcasPorId(id);
            return ResponseEntity.ok(assembler.toModel(marcas));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marcas>> agregarMarcas(@RequestBody Marcas marcas) {
        try {
            Marcas newMarcas = marcasService.guardarMarcas(marcas);
            return ResponseEntity
                .created(linkTo(methodOn(ProductoController.class).buscarPorId(newMarcas.getIdMarcas())).toUri())
                .body(assembler.toModel(newMarcas));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marcas>> editarRegiom(@PathVariable Integer id, @RequestBody Marcas marcas) {
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

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarMarcas(@PathVariable Integer id) {
        try {
            marcasService.eliminarMarcas(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
