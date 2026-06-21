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

import com.XP_Shop.Bloque_Usuario.dto.RegionDTO;
import com.XP_Shop.Bloque_Usuario.model.Region;
import com.XP_Shop.Bloque_Usuario.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/region")
@Tag(name = "Region", description = "Endpoints para manejar el listado de regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    @Operation(summary = "Listar todas las regiones", description = "Te devuelve la lista completa con todas las regiones guardadas en el sistema")
    public ResponseEntity<List<RegionDTO>> todasLasRegiones() {
        List<RegionDTO> region = regionService.listarRegion();
        if (region.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar region por ID", description = "Te devuelve la region con solo su ID")
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
    public ResponseEntity<Region> agregarRegion(@RequestBody Region region) {
        try {
            regionService.guardarRegion(region);
            return new ResponseEntity<>(region, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar la region", description = "Edita Region existente en el sistema")
    public ResponseEntity<Region> editarRegion(@PathVariable Integer id, @RequestBody Region region) {
        try {
            regionService.guardarRegion(region);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar region", description = "Actualiza datos de una region existente en el sistema")
    public ResponseEntity<Region> actualizarRegion(@PathVariable Integer id, @RequestBody Region region) {
        try {
            regionService.actualizarRegion(id, region);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar la region", description = "Elimina region existente en el sistema")
    public ResponseEntity<String> eliminarRegion(@PathVariable Integer id) {
        try {
            regionService.eliminarRegion(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
