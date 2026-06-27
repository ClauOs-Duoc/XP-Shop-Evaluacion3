package com.XP_Shop.Bloque_Usuario.controller;

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

import com.XP_Shop.Bloque_Usuario.dto.RegionDTO;
import com.XP_Shop.Bloque_Usuario.model.Region;
import com.XP_Shop.Bloque_Usuario.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/region")
@Tag(name = "Region", description = "Endpoints para manejar el listado de regiones")
public class RegionController {

    private final RegionService regionService;

    RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las regiones", description = "Te devuelve la lista completa de regiones que hay guardadas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de regiones obtenida con exito"),
        @ApiResponse(responseCode = "204", description = "No hay contenido en la lista")
    })
    public ResponseEntity<List<RegionDTO>> todasLasRegiones() {
        List<RegionDTO> region = regionService.listarRegion();
        if (region.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar region por ID", description = "obtiene todos los detalles de una region usando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Region encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Region no encontrada")
    })
    public ResponseEntity<RegionDTO> buscarPorId(@PathVariable Integer id) {
        try {
            RegionDTO region = regionService.buscarRegionPorId(id);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar nueva region", description = "Crea una nueva region en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Region creada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Region> agregarRegion(@Valid @RequestBody Region region) {
        try {
            regionService.guardarRegion(region);
            return new ResponseEntity<>(region, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar region existente", description = "Edita region existente en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Region actualizada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Region no encontrada")
    })
    public ResponseEntity<Region> editarRegion(@PathVariable Integer id, @Valid @RequestBody Region region) {
        try {
            regionService.guardarRegion(region);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar region existente", description = "Actualiza region existente en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Region actualizada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Region no encontrada")
    })
    public ResponseEntity<Region> actualizarRegion(@PathVariable Integer id, @Valid @RequestBody Region region) {
        try {
            regionService.actualizarRegion(id, region);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una region", description = "Elimina region existente en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Region eliminada"),
        @ApiResponse(responseCode = "404", description = "Region no encontrada")
    })
    public ResponseEntity<String> eliminarRegion(@PathVariable Integer id) {
        try {
            regionService.eliminarRegion(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
