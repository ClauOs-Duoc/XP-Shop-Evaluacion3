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

import com.XP_Shop.Bloque_Boleta.dto.DetalleBoletaDTO;
import com.XP_Shop.Bloque_Boleta.model.DetalleBoleta;
import com.XP_Shop.Bloque_Boleta.service.DetalleBoletaService;

@RestController
@RequestMapping("/api/v1/detalleBoleta")
public class DetalleBoletaController {

    @Autowired
    private DetalleBoletaService detalleBoletaService;

    @GetMapping
    public ResponseEntity<List<DetalleBoletaDTO>> todosLosDetalleBoleta() {
        List<DetalleBoletaDTO> detalleBoleta = detalleBoletaService.listarDetalleBoleta();
        if (detalleBoleta.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detalleBoleta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleBoletaDTO> buscarPorId(Integer id){
        try {
            DetalleBoletaDTO detalleBoleta = detalleBoletaService.buscarDetalleBoletaPorId(id);
            return new ResponseEntity<>(detalleBoleta, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleBoleta> agregarDetalleBoleta(@RequestBody DetalleBoleta detalleBoleta) {
        try {
            detalleBoletaService.guardarDetalleBoleta(detalleBoleta);
            return new ResponseEntity<>(detalleBoleta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DetalleBoleta> editarRegiom(@PathVariable Integer id, @RequestBody DetalleBoleta detalleBoleta) {
        try {
            detalleBoletaService.guardarDetalleBoleta(detalleBoleta);
            return new ResponseEntity<>(detalleBoleta, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleBoleta> actualizarDetalleBoleta(@PathVariable Integer id, @RequestBody DetalleBoleta detalleBoleta){
        try{
            detalleBoletaService.actualizarDetalleBoleta(id, detalleBoleta);
            return new ResponseEntity<>(detalleBoleta, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDetalleBoleta(@PathVariable Integer id) {
        try {
            detalleBoletaService.eliminarDetalleBoleta(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
