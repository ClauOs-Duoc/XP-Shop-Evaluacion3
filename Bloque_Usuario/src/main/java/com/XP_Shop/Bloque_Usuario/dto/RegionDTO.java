package com.XP_Shop.Bloque_Usuario.dto;

import java.util.List;
import lombok.Data;

@Data
public class RegionDTO {

    private Integer idRegion;
    private String nombreRegion;
    private List<String> nombreComunas;
    
}
