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

import com.XP_Shop.Bloque_Boleta.dto.MetodoEnvioDTO;
import com.XP_Shop.Bloque_Boleta.model.MetodoEnvio;
import com.XP_Shop.Bloque_Boleta.service.MetodoEnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/metodoEnvio")
@Tag(name = "Metodo Envio Controller", description = "Endpoints para la gestion de metodos de envio")
public class MetodoEnvioController {

    private final MetodoEnvioService metodoEnvioService;

    MetodoEnvioController(MetodoEnvioService metodoEnvioService) {
        this.metodoEnvioService = metodoEnvioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los metodos de envio", description = "Obtiene una lista de todos los métodos de envio disponibles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodos de envio encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay metodos de envio disponibles")
    })
    public ResponseEntity<List<MetodoEnvioDTO>> todosLosMetodoEnvio() {
        List<MetodoEnvioDTO> metodoEnvio = metodoEnvioService.listarMetodoEnvio();
        if (metodoEnvio.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodoEnvio, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar metodo de envío por ID", description = "Obtiene un metodo de envio especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodo de envio encontrado"),
        @ApiResponse(responseCode = "404", description = "Metodo de envio no encontrado")
    })
    public ResponseEntity<MetodoEnvioDTO> buscarPorId(@PathVariable Integer id){
        try {
            MetodoEnvioDTO metodoEnvio = metodoEnvioService.buscarMetodoEnvioPorId(id);
            return new ResponseEntity<>(metodoEnvio, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar metodo de envio", description = "Crea un nuevo método de envio")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Metodo de envio creado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<MetodoEnvio> agregarMetodoEnvio(@RequestBody MetodoEnvio metodoEnvio) {
        try {
            metodoEnvioService.guardarMetodoEnvio(metodoEnvio);
            return new ResponseEntity<>(metodoEnvio, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar metodo de envio", description = "Actualiza un metodo de envio especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodo de envio actualizado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Metodo de envio no encontrado")
    })
    public ResponseEntity<MetodoEnvio> editarMetodoEnvio(@PathVariable Integer id, @RequestBody MetodoEnvio metodoEnvio) {
        try {
            metodoEnvioService.guardarMetodoEnvio(metodoEnvio);
            return new ResponseEntity<>(metodoEnvio, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar metodo de envio", description = "Actualiza un metodo de envio especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodo de envio actualizado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Metodo de envio no encontrado")
    })
    public ResponseEntity<MetodoEnvio> actualizarMetodoEnvio(@PathVariable Integer id, @RequestBody MetodoEnvio metodoEnvio){
        try{
            metodoEnvioService.actualizarMetodoEnvio(id, metodoEnvio);
            return new ResponseEntity<>(metodoEnvio, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar metodo de envio", description = "Elimina un metodo de envio especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodo de envio eliminado"),
        @ApiResponse(responseCode = "404", description = "Metodo de envio no encontrado")
    })
    public ResponseEntity<String> eliminarMetodoEnvio(@PathVariable Integer id) {
        try {
            metodoEnvioService.eliminarMetodoEnvio(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
