package com.XP_Shop.Bloque_Boleta.dto;

import java.util.List;

import lombok.Data;

@Data
public class MetodoPagoDTO {

    private Integer idMetodoPago;
    private String nombreMetodoPago;
    private List<Integer> boletas;
    
}
