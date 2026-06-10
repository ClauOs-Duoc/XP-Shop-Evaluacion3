package com.XP_Shop.Bloque_Producto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagen;

    @NotBlank (message = "Es obligatorio llenar este apartado")
    @Size(min = 2, max = 30, message = "Debe llenar el apartado con entre 2 y 100 caracteres")
    @Column(nullable = false, length = 30)
    private String nombreImagen;

    @ManyToOne
    @JoinColumn(name = "productoId")
    private Producto producto;
    
}
