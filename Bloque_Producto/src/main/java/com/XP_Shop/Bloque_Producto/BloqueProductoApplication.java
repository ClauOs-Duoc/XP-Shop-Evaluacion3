package com.XP_Shop.Bloque_Producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BloqueProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloqueProductoApplication.class, args);
	}

}
