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

import com.XP_Shop.Bloque_Producto.assemblers.ProductosModelAssemble;
import com.XP_Shop.Bloque_Producto.model.Productos;
import com.XP_Shop.Bloque_Producto.service.ProductosService;


@RestController
@RequestMapping("/api/v1/productos")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @Autowired
    private ProductosModelAssemble assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Productos>> todosLosProductos(){
        List<EntityModel<Productos>> productos = productosService.listaProductos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(productos,
            linkTo(methodOn(ProductosController.class).todosLosProductos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Productos>> buscarPorId(Integer id){
        try {
            Productos productos = productosService.buscarProductosPorId(id);
            return ResponseEntity.ok(assembler.toModel(productos));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Productos>> agregarProductos(@RequestBody Productos productos) {
        try {
            Productos newProductos = productosService.guardarProductos(productos);
            return ResponseEntity
                .created(linkTo(methodOn(ProductoController.class).buscarPorId(newProductos.getIdProductos())).toUri())
                .body(assembler.toModel(newProductos));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Productos>> editarRegiom(@PathVariable Integer id, @RequestBody Productos productos) {
        try {
            productosService.guardarProductos(productos);
            Productos productosPatch = productos;
            return ResponseEntity.ok(assembler.toModel(productosPatch));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Productos>> actualizarProductos(@PathVariable Integer id, @RequestBody Productos productos) {
        try{
            productosService.actualizarProductos(id, productos);
            Productos productosUpdate = productosService.guardarProductos(productos);
            return ResponseEntity.ok(assembler.toModel(productosUpdate));
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarProductos(@PathVariable Integer id) {
        try {
            productosService.eliminarProductos(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
