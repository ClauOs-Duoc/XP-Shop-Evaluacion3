package com.XP_Shop.Bloque_Usuario.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer idUsuario;
    private String nombreUsuario;
    private String correo;
    private LocalDate fechaNacimiento;
    private List<Integer> idBoleta;
    private String nombreComuna;
    
}
