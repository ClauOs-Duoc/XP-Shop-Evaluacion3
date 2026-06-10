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

import com.XP_Shop.Bloque_Producto.dto.MarcaDTO;
import com.XP_Shop.Bloque_Producto.model.Marca;
import com.XP_Shop.Bloque_Producto.service.MarcaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/marca")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<MarcaDTO>> todasLaMarca() {
        List<MarcaDTO> marca = marcaService.listarMarca();
        if (marca.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> buscarPorId(Integer id){
        try {
            MarcaDTO marca = marcaService.buscarMarcaPorId(id);
            return new ResponseEntity<>(marca, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Marca> agregarMarca(@Valid @RequestBody Marca marca) {
        try {
            marcaService.guardarMarca(marca);
            return new ResponseEntity<>(marca, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Marca> editarRegiom(@PathVariable Integer id, @RequestBody Marca marca) {
        try {
            marcaService.guardarMarca(marca);
            return new ResponseEntity<>(marca, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Integer id, @RequestBody Marca marca){
        try{
            marcaService.actualizarMarca(id, marca);
            return new ResponseEntity<>(marca, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMarca(@PathVariable Integer id) {
        try {
            marcaService.eliminarMarca(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
