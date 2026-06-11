package com.XP_Shop.Bloque_Boleta.controller;

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

import com.XP_Shop.Bloque_Boleta.dto.MetodoEnvioDTO;
import com.XP_Shop.Bloque_Boleta.model.MetodoEnvio;
import com.XP_Shop.Bloque_Boleta.service.MetodoEnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/metodoEnvio")
@Tag(name = "Metodo Envio Controller", description = "Endpoints para la gestión de métodos de envío")
public class MetodoEnvioController {

    @Autowired
    private MetodoEnvioService metodoEnvioService;

    @GetMapping
    @Operation(summary = "Listar todos los métodos de envío", description = "Obtiene una lista de todos los métodos de envío disponibles")
    public ResponseEntity<List<MetodoEnvioDTO>> todosLosMetodoEnvio() {
        List<MetodoEnvioDTO> metodoEnvio = metodoEnvioService.listarMetodoEnvio();
        if (metodoEnvio.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodoEnvio, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar método de envío por ID", description = "Obtiene un método de envío específico por su ID")
    public ResponseEntity<MetodoEnvioDTO> buscarPorId(@PathVariable Integer id){
        try {
            MetodoEnvioDTO metodoEnvio = metodoEnvioService.buscarMetodoEnvioPorId(id);
            return new ResponseEntity<>(metodoEnvio, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar método de envío", description = "Crea un nuevo método de envío")
    public ResponseEntity<MetodoEnvio> agregarMetodoEnvio(@RequestBody MetodoEnvio metodoEnvio) {
        try {
            metodoEnvioService.guardarMetodoEnvio(metodoEnvio);
            return new ResponseEntity<>(metodoEnvio, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar método de envío", description = "Actualiza un método de envío específico por su ID")
    public ResponseEntity<MetodoEnvio> editarRegiom(@PathVariable Integer id, @RequestBody MetodoEnvio metodoEnvio) {
        try {
            metodoEnvioService.guardarMetodoEnvio(metodoEnvio);
            return new ResponseEntity<>(metodoEnvio, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar método de envío", description = "Actualiza un método de envío específico por su ID")
    public ResponseEntity<MetodoEnvio> actualizarMetodoEnvio(@PathVariable Integer id, @RequestBody MetodoEnvio metodoEnvio){
        try{
            metodoEnvioService.actualizarMetodoEnvio(id, metodoEnvio);
            return new ResponseEntity<>(metodoEnvio, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar método de envío", description = "Elimina un método de envío específico por su ID")
    public ResponseEntity<String> eliminarMetodoEnvio(@PathVariable Integer id) {
        try {
            metodoEnvioService.eliminarMetodoEnvio(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
