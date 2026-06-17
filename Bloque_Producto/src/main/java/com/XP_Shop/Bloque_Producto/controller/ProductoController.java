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

import com.XP_Shop.Bloque_Producto.assemblers.ProductoModelAssemble;
import com.XP_Shop.Bloque_Producto.dto.ProductoDTO;
import com.XP_Shop.Bloque_Producto.model.Producto;
import com.XP_Shop.Bloque_Producto.service.ProductoService;

@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssemble assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ProductoDTO>> todosLosProducto(){
        List<EntityModel<ProductoDTO>> producto = productoService.listarProducto().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(producto,
            linkTo(methodOn(ProductoController.class).todosLosProducto()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> buscarPorId(Integer id){
        try {
            ProductoDTO producto = productoService.buscarProductoPorId(id);
            return ResponseEntity.ok(assembler.toModel(producto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> agregarProductos(@RequestBody Producto producto) {
        try {
            ProductoDTO newProducto = productoService.guardarProducto(producto);
            return ResponseEntity
                .created(linkTo(methodOn(ProductoController.class).buscarPorId(newProducto.getIdProducto())).toUri())
                .body(assembler.toModel(newProducto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        try{
            productoService.actualizarProducto(id, producto);
            ProductoDTO productoUpdate = productoService.guardarProducto(producto);
            return ResponseEntity.ok(assembler.toModel(productoUpdate));
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> editarRegiom(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            ProductoDTO productoPatch = productoService.guardarProducto(producto);
            return ResponseEntity.ok(assembler.toModel(productoPatch));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id) {
        try {
            productoService.eliminarProducto(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
