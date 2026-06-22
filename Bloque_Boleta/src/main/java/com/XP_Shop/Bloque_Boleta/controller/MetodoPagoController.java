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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Listar todos los metodos de pago", description = "Obtiene una lista de todos los metodos de pago disponibles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodos de pago encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay metodos de pago disponibles")
    })
    public ResponseEntity<List<MetodoPagoDTO>> todosLosMetodoPago() {
        List<MetodoPagoDTO> metodoPago = metodoPagoService.listarMetodoPago();
        if (metodoPago.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodoPago, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar metodo de pago por ID", description = "Obtiene un metodo de pago especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodo de pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Metodo de pago no encontrado")
    })
    public ResponseEntity<MetodoPagoDTO> buscarPorId(Integer id){
        try {
            MetodoPagoDTO metodoPago = metodoPagoService.buscarMetodoPagoPorId(id);
            return new ResponseEntity<>(metodoPago, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar metodo de pago", description = "Crea un nuevo metodo de pago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Metodo de pago creado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<MetodoPago> agregarMetodoPago(@RequestBody MetodoPago metodoPago) {
        try {
            metodoPagoService.guardarMetodoPago(metodoPago);
            return new ResponseEntity<>(metodoPago, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar metodo de pago", description = "Actualiza un metodo de pago especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodo de pago actualizado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Metodo de pago no encontrado")
    })
    public ResponseEntity<MetodoPago> editarMetodoPago(@PathVariable Integer id, @RequestBody MetodoPago metodoPago) {
        try {
            metodoPagoService.guardarMetodoPago(metodoPago);
            return new ResponseEntity<>(metodoPago, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar metodo de pago", description = "Actualiza un metodo de pago especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodo de pago actualizado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Metodo de pago no encontrado")
    })
    public ResponseEntity<MetodoPago> actualizarMetodoPago(@PathVariable Integer id, @RequestBody MetodoPago metodoPago){
        try{
            metodoPagoService.actualizarMetodoPago(id, metodoPago);
            return new ResponseEntity<>(metodoPago, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar metodo de pago", description = "Elimina un metodo de pago especifico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metodo de pago eliminado"),
        @ApiResponse(responseCode = "404", description = "Metodo de pago no encontrado")
    })
    public ResponseEntity<String> eliminarMetodoPago(@PathVariable Integer id) {
        try {
            metodoPagoService.eliminarMetodoPago(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
