package com.XP_Shop.Bloque_Producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.XP_Shop.Bloque_Producto.model.Categorias;
import com.XP_Shop.Bloque_Producto.service.CategoriasService;



@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasService categoriasService;

    @GetMapping
    public ResponseEntity<List<Categorias>> todasLasCategorias() {
        List<Categorias> categorias = categoriasService.listaCategorias();
        if (categorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorias> buscarPorId(Integer id){
        try {
            Categorias categorias = categoriasService.buscarCategoriasPorId(id);
            return new ResponseEntity<>(categorias, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Categorias> agregarCategorias(@RequestBody Categorias categorias) {
        try {
            categoriasService.guardarCategorias(categorias);
            return new ResponseEntity<>(categorias, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Categorias> editarRegiom(@PathVariable Integer id, @RequestBody Categorias categorias) {
        try {
            categoriasService.guardarCategorias(categorias);
            return new ResponseEntity<>(categorias, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorias> actualizarCategorias(@PathVariable Integer id, @RequestBody Categorias categorias) {
        try{
            categoriasService.actualizarCategorias(id, categorias);
            return new ResponseEntity<>(categorias, HttpStatus.OK);
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
