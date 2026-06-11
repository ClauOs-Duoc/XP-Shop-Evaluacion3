package com.XP_Shop.Bloque_Boleta.controller;

import java.util.List;

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

import com.XP_Shop.Bloque_Boleta.dto.BoletaDTO;
import com.XP_Shop.Bloque_Boleta.model.Boleta;
import com.XP_Shop.Bloque_Boleta.service.BoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/boleta")
@Tag(name = "Boleta Controller", description = "Endpoints para la gestión de boletas")
public class BoletaController {

    private final BoletaService boletaService;

    BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @GetMapping
    public ResponseEntity<List<BoletaDTO>> listarBoleta() {
        List<BoletaDTO> boleta = boletaService.listarBoleta();
        if (boleta.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boleta, HttpStatus.OK);
    }
    @GetMapping
    @Operation(summary = "Listar todas las boletas", description = "Obtiene una lista de todas las boletas disponibles")
    public ResponseEntity<List<BoletaDTO>> listarBoletas() {
        List<BoletaDTO> boletas = boletaService.listarBoleta();
        if (boletas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boletas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoletaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            BoletaDTO boleta = boletaService.buscarBoletaPorId(id);
            return new ResponseEntity<>(boleta, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Boleta> agregarBoleta(@Valid @RequestBody Boleta boleta) {
        try {
            boletaService.guardarBoleta(boleta);
            return new ResponseEntity<>(boleta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boleta> editarBoleta(@PathVariable Integer id, @RequestBody Boleta boleta) {
        try {
            boletaService.guardarBoleta(boleta);
            return new ResponseEntity<>(boleta, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boleta> actualizarBoleta(@PathVariable Integer id, @RequestBody Boleta boleta) {
        try {
            boletaService.actualizarBoleta(id, boleta);
            return new ResponseEntity<>(boleta, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarBoleta(@PathVariable Integer id) {
        try {
            boletaService.eliminarBoleta(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
