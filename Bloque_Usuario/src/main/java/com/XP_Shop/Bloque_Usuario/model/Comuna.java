package com.XP_Shop.Bloque_Usuario.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "comuna")
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComuna;

    @NotBlank (message = "Es obligatorio llenar este apartado")
    @Size(min = 2, max = 100, message = "Debe llenar el apartado con entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombreComuna;

    @ManyToOne
    @JoinColumn(name = "region_Id")
    private Region region;
 
    @OneToMany(mappedBy = "comuna")
    private List<Usuario> usuario;
    
}
