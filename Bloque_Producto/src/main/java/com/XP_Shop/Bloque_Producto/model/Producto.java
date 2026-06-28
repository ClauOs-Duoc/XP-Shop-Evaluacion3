package com.XP_Shop.Bloque_Producto.model;

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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @NotBlank (message = "Es obligatorio llenar este apartado")
    @Size(min = 2, max = 30, message = "Debe llenar el apartado con entre 2 y 100 caracteres")
    @Column(nullable = false, length = 30)
    private String nombreProducto;

    @NotNull (message = "Debe asignar un precio al producto")
    @Min(value = 1, message = "El producto no puede tener un precio inferior a 1")
    @Max(value = 2000000, message = "El producto no puede tener un valor superior a 20000000")
    @Column(nullable = false)
    private Double precio;

    @NotBlank (message = "Es obligatorio llenar este apartado")
    @Size(min = 2, max = 100, message = "Debe llenar el apartado con entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String descripcionProducto;

    @NotNull
    @Min(value = 0, message = "El producto no puede tener una cantidad inferior a 0")
    @Column(nullable = false)
    private Integer stock;
    
    @ManyToOne
    @JoinColumn(name = "marcas_id")
    private Marcas marcas;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categorias categorias;

    @ManyToOne
    @JoinColumn(name = "productos_id")
    private Productos productos;

    @OneToMany(mappedBy = "producto")
    private List<Imagen> imagenes;
    
}
