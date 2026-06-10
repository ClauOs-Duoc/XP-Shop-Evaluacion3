package com.XP_Shop.Bloque_Boleta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.XP_Shop.Bloque_Boleta.model.MetodoEnvio;

@Repository
public interface MetodoEnvioRepository extends JpaRepository<MetodoEnvio, Integer>{
    
}
