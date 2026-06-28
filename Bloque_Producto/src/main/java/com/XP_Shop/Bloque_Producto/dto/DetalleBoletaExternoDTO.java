package com.XP_Shop.Bloque_Producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleBoletaExternoDTO {

    private Integer idDetalleBoleta;
    private Integer cantidad;
    private Double subtotal;
    private Integer productoId;

}
