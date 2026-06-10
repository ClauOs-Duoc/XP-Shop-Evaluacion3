package com.XP_Shop.Bloque_Producto.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.XP_Shop.Bloque_Producto.dto.ImagenDTO;
import com.XP_Shop.Bloque_Producto.model.Imagen;
import com.XP_Shop.Bloque_Producto.repository.ImagenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImagenService {

    private static final Logger log = LoggerFactory.getLogger(ImagenService.class);

    @Autowired
    private ImagenRepository imagenRepository;

    private ImagenDTO convertirImagenADTO(Imagen imagen){
        log.info("Convirtiendo imagen a DTO: {}", imagen.getIdImagen());
        ImagenDTO dto = new ImagenDTO();
        dto.setIdImagen(imagen.getIdImagen());
        dto.setNombreImagen(imagen.getNombreImagen());

        if(imagen.getProducto() != null){
            dto.setIdProducto(imagen.getProducto().getIdProducto());
        }
        return dto;
    }

    public List<ImagenDTO> listarImagen() {
        log.info("Listando todas las imagenes");
        return imagenRepository.findAll().stream()
                    .map(this::convertirImagenADTO)
                    .toList();
    }

    public ImagenDTO buscarImagenPorId(Integer id) {
        log.info("Buscando imagen por ID: {}", id);
        Imagen imagen = imagenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La imagen no existe."));
        return convertirImagenADTO(imagen);
    }

    public ImagenDTO guardarImagen(Imagen imagen) {
        log.info("Guardando imagen: {}", imagen.getNombreImagen());
        Imagen savedImagen = imagenRepository.save(imagen);
        log.info("Imagen guardada con ID: {}", savedImagen.getIdImagen());
        return convertirImagenADTO(savedImagen);
    }

    public ImagenDTO actualizarImagen(Integer id, Imagen imagen) {
        log.info("Actualizando imagen con ID: {}", id);
        Imagen imagenExistente = imagenRepository.findById(id).orElseThrow(() -> new RuntimeException("La imagen no existe."));
        if (imagen.getNombreImagen() != null) {
            imagenExistente.setNombreImagen(imagen.getNombreImagen());
        }
        if (imagen.getProducto() != null) {
            imagenExistente.setProducto(imagen.getProducto());
        }

        Imagen updatedImagen = imagenRepository.save(imagenExistente);
        log.info("Imagen actualizada con ID: {}", updatedImagen.getIdImagen());
        return convertirImagenADTO(updatedImagen);
    }

    public Void eliminarImagen(Integer id) {
        log.info("Eliminando imagen con ID: {}", id);
        Imagen imagen = imagenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede eliminar la imagen con ID " + id + " no existe."));
        imagenRepository.delete(imagen);
        log.info("Imagen eliminada con ID: {}", id);
        return null;
    }
    
}
