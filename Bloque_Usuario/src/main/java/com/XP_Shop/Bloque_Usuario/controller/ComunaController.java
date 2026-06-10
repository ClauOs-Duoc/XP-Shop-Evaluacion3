package com.XP_Shop.Bloque_Usuario.controller;

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

import com.XP_Shop.Bloque_Usuario.dto.ComunaDTO;
import com.XP_Shop.Bloque_Usuario.model.Comuna;
import com.XP_Shop.Bloque_Usuario.service.ComunaService;

@RestController
@RequestMapping("/api/v1/comuna")

public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> todasLasComunas() {
        List<ComunaDTO> comuna = comunaService.listarComuna();
        if (comuna.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comuna, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunaDTO> buscarPorId(Integer id){
        try {
            ComunaDTO comuna = comunaService.buscarComunaPorId(id);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Comuna> agregarComuna(@RequestBody Comuna comuna) {
        try {
            comunaService.guardarComuna(comuna);
            return new ResponseEntity<>(comuna, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comuna> editarRegiom(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            comunaService.guardarComuna(comuna);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> actualizarComuna(@PathVariable Integer id, @RequestBody Comuna comuna){
        try{
            comunaService.actualizarComuna(id, comuna);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarComuna(@PathVariable Integer id) {
        try {
            comunaService.eliminarComuna(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
