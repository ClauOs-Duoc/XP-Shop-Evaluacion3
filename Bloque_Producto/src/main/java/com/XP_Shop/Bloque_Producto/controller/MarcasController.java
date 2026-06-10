package com.XP_Shop.Bloque_Producto.controller;

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

import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.service.MarcasService;

@RestController
@RequestMapping("/api/v1/marcas")
public class MarcasController {

    @Autowired
    private MarcasService marcasService;

    @GetMapping
    public ResponseEntity<List<Marcas>> todasLasMarcas() {
        List<Marcas> marcas = marcasService.listaMarcas();
        if (marcas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marcas> buscarPorId(Integer id){
        try {
            Marcas marcas = marcasService.buscarMarcasPorId(id);
            return new ResponseEntity<>(marcas, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Marcas> agregarMarcas(@RequestBody Marcas marcas) {
        try {
            marcasService.guardarMarcas(marcas);
            return new ResponseEntity<>(marcas, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Marcas> editarRegiom(@PathVariable Integer id, @RequestBody Marcas marcas) {
        try {
            marcasService.guardarMarcas(marcas);
            return new ResponseEntity<>(marcas, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marcas> actualizarMarcas(@PathVariable Integer id, @RequestBody Marcas marcas) {
        try{
            marcasService.actualizarMarcas(id, marcas);
            return new ResponseEntity<>(marcas, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMarcas(@PathVariable Integer id) {
        try {
            marcasService.eliminarMarcas(id);
            return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
