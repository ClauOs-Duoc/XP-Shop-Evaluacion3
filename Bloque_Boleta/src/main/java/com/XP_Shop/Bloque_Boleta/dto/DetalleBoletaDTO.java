package com.XP_Shop.Bloque_Boleta.dto;

import lombok.Data;

@Data
public class DetalleBoletaDTO {

    private Integer idDetalleBoleta;
    private Integer cantidad;
    private Double subtotal;
    private Integer boleta;
    private Integer productos;
    
}
