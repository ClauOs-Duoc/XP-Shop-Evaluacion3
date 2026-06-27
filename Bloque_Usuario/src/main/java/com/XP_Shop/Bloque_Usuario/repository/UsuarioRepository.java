package com.XP_Shop.Bloque_Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.XP_Shop.Bloque_Usuario.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
}
