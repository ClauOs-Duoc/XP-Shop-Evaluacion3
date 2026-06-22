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

import com.XP_Shop.Bloque_Boleta.dto.DetalleBoletaDTO;
import com.XP_Shop.Bloque_Boleta.model.DetalleBoleta;
import com.XP_Shop.Bloque_Boleta.service.DetalleBoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/detalleBoleta")
@Tag(name = "Detalle Boleta Controller", description = "Endpoints para la gestion de detalles de boletas")
public class DetalleBoletaController {

    private final DetalleBoletaService detalleBoletaService;

    public DetalleBoletaController(DetalleBoletaService detalleBoletaService) {
        this.detalleBoletaService = detalleBoletaService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los detalles de boletas", description = "Obtiene una lista de todos los detalles de boletas disponibles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalles de boletas encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay detalles de boletas disponibles")
    })
    public ResponseEntity<List<DetalleBoletaDTO>> todosLosDetalleBoleta() {
        List<DetalleBoletaDTO> detalleBoleta = detalleBoletaService.listarDetalleBoleta();
        if (detalleBoleta.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detalleBoleta, HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    @Operation(summary = "Buscar detalle de boleta por ID", description = "Obtiene un detalle de boleta especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle de boleta encontrado"),
        @ApiResponse(responseCode = "404", description = "Detalle de boleta no encontrado")
    })
    public ResponseEntity<DetalleBoletaDTO> buscarPorId(@PathVariable Integer id){
        try {
            DetalleBoletaDTO detalleBoleta = detalleBoletaService.buscarDetalleBoletaPorId(id);
            return new ResponseEntity<>(detalleBoleta, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar detalle de boleta", description = "Crea un nuevo detalle de boleta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Detalle de boleta creado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<DetalleBoleta> agregarDetalleBoleta(@RequestBody DetalleBoleta detalleBoleta) {
        try {
            detalleBoletaService.guardarDetalleBoleta(detalleBoleta);
            return new ResponseEntity<>(detalleBoleta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar detalle de boleta", description = "Actualiza un detalle de boleta especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle de boleta actualizado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Detalle de boleta no encontrado")
    })
    public ResponseEntity<DetalleBoleta> editarDetalleBoleta(@PathVariable Integer id, @RequestBody DetalleBoleta detalleBoleta) {
        try {
            detalleBoletaService.actualizarDetalleBoleta(id, detalleBoleta);
            return new ResponseEntity<>(detalleBoleta, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar detalle de boleta", description = "Actualiza un detalle de boleta especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle de boleta actualizado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Detalle de boleta no encontrado")
    })
    public ResponseEntity<DetalleBoleta> actualizarDetalleBoleta(@PathVariable Integer id, @RequestBody DetalleBoleta detalleBoleta){
        try{
            detalleBoletaService.actualizarDetalleBoleta(id, detalleBoleta);
            return new ResponseEntity<>(detalleBoleta, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar detalle de boleta", description = "Elimina un detalle de boleta especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle de boleta eliminado"),
        @ApiResponse(responseCode = "404", description = "Detalle de boleta no encontrado")
    })
    public ResponseEntity<String> eliminarDetalleBoleta(@PathVariable Integer id) {
        try {
            detalleBoletaService.eliminarDetalleBoleta(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
