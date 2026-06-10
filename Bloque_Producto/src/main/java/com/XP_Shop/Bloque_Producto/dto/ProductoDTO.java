package com.XP_Shop.Bloque_Producto.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductoDTO {

    private Integer idProducto;
    private String nombreProducto;
    private Double precio;
    private String descripcionProducto;
    private Integer stock;
    private Integer idProductos;
    private Integer idMarcas;
    private Integer idCategorias;
    private List<String> imagenes;
    
}
