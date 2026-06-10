package com.XP_Shop.Bloque_Boleta.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BoletaDTO {

    private Integer idBoleta;
    private LocalDate fechaCompra;
    private Double totalCompra;
    private String usuario;
    private String metodoEnvio;
    private String metodoPago;
    private Integer detalleBoleta;
    
}
