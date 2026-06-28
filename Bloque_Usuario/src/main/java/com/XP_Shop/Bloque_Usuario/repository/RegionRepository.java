package com.XP_Shop.Bloque_Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.XP_Shop.Bloque_Usuario.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>{
    
}
