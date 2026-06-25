package com.XP_Shop.Bloque_Usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BloqueUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloqueUsuarioApplication.class, args);
	}

}
