package com.XP_Shop.Bloque_Boleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BloqueBoletaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloqueBoletaApplication.class, args);
	}

}
//url de la pagina http://localhost:8081/doc/swagger-ui/index.html