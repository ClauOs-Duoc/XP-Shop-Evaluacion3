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

import com.XP_Shop.Bloque_Boleta.dto.MetodoPagoDTO;
import com.XP_Shop.Bloque_Boleta.model.MetodoPago;
import com.XP_Shop.Bloque_Boleta.service.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/metodoPago")
@Tag(name = "Metodo Pago Controller", description = "Endpoints para la gestión de métodos de pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    MetodoPagoController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los métodos de pago", description = "Obtiene una lista de todos los métodos de pago disponibles")
    public ResponseEntity<List<MetodoPagoDTO>> todosLosMetodoPago() {
        List<MetodoPagoDTO> metodoPago = metodoPagoService.listarMetodoPago();
        if (metodoPago.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodoPago, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar método de pago por ID", description = "Obtiene un método de pago específico por su ID")
    public ResponseEntity<MetodoPagoDTO> buscarPorId(Integer id){
        try {
            MetodoPagoDTO metodoPago = metodoPagoService.buscarMetodoPagoPorId(id);
            return new ResponseEntity<>(metodoPago, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar método de pago", description = "Crea un nuevo método de pago")
    public ResponseEntity<MetodoPago> agregarMetodoPago(@RequestBody MetodoPago metodoPago) {
        try {
            metodoPagoService.guardarMetodoPago(metodoPago);
            return new ResponseEntity<>(metodoPago, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar método de pago", description = "Actualiza un método de pago específico por su ID")
    public ResponseEntity<MetodoPago> editarRegiom(@PathVariable Integer id, @RequestBody MetodoPago metodoPago) {
        try {
            metodoPagoService.guardarMetodoPago(metodoPago);
            return new ResponseEntity<>(metodoPago, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar método de pago", description = "Actualiza un método de pago específico por su ID")
    public ResponseEntity<MetodoPago> actualizarMetodoPago(@PathVariable Integer id, @RequestBody MetodoPago metodoPago){
        try{
            metodoPagoService.actualizarMetodoPago(id, metodoPago);
            return new ResponseEntity<>(metodoPago, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar método de pago", description = "Elimina un método de pago específico por su ID")
    public ResponseEntity<String> eliminarMetodoPago(@PathVariable Integer id) {
        try {
            metodoPagoService.eliminarMetodoPago(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
