package com.XP_Shop.Bloque_Producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.XP_Shop.Bloque_Producto.model.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Integer>{
    
}
