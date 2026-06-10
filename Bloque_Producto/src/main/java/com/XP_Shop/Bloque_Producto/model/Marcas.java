package com.XP_Shop.Bloque_Producto.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marcas")
public class Marcas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMarcas;

    @OneToMany(mappedBy = "marcas")
    private List<Marca> marcas;
 
    @OneToMany(mappedBy = "marcas")
    private List<Producto> productos;
    
}
