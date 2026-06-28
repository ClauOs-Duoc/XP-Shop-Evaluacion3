package com.XP_Shop.Bloque_Producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.XP_Shop.Bloque_Producto.dto.DetalleBoletaExternoDTO;

import reactor.core.publisher.Mono;

@Service
public class ProductosValidations {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public DetalleBoletaExternoDTO obtenerDetalleBoleta(Integer id) {
        DetalleBoletaExternoDTO detalleVacio = new DetalleBoletaExternoDTO();
        try {
            DetalleBoletaExternoDTO resultado = webClientBuilder.build()
                .get()
                // nombre del servicio en Eureka, no localhost
                .uri("http://bloque-boleta/api/v1/detalle-boleta/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToMono(DetalleBoletaExternoDTO.class)
                .block();

            if (resultado != null) {
                return resultado;
            }

            detalleVacio.setIdDetalleBoleta(id);
            detalleVacio.setCantidad(0);
            detalleVacio.setSubtotal(0.0);
            return detalleVacio;

        } catch (Exception e) {
            detalleVacio.setIdDetalleBoleta(id);
            detalleVacio.setCantidad(0);
            detalleVacio.setSubtotal(0.0);
            return detalleVacio;
        }
    }

}
