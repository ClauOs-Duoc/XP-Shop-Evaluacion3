package com.XP_Shop.Bloque_Producto.model;

import java.util.List;

import jakarta.persistence.Column;
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
@Table(name = "productos")
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProductos;

    @OneToMany(mappedBy = "productos")
    private List<Producto> producto;
 
    @Column(name = "id_detalle_boleta")
    private Integer id_detalleBoletas;
    
}
