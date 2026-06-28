package com.XP_Shop.Bloque_Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.XP_Shop.Bloque_Usuario.model.Comuna;
import com.XP_Shop.Bloque_Usuario.model.Region;
import com.XP_Shop.Bloque_Usuario.model.Usuario;
import com.XP_Shop.Bloque_Usuario.repository.ComunaRepository;
import com.XP_Shop.Bloque_Usuario.repository.RegionRepository;
import com.XP_Shop.Bloque_Usuario.repository.UsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ComunaRepository comunaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        
        List<Region> regionesGuardadas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Region region = new Region();
            List<Comuna> comunas1 = new ArrayList<>();

            
            region.setNombreRegion(faker.address().state());
            region.setComunas(comunas1);

            regionesGuardadas.add(regionRepository.save(region));
        }

        
        List<Comuna> comunasGuardadas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Comuna comuna = new Comuna();
            
            
            Region regionExistente = regionesGuardadas.get(i);
            List<Usuario> usuarios1 = new ArrayList<>();

            
            comuna.setNombreComuna(faker.address().city());
            comuna.setRegion(regionExistente);
            comuna.setUsuario(usuarios1);

            comunasGuardadas.add(comunaRepository.save(comuna));
        }

        
        for (int i = 0; i < 3; i++) {
            Usuario usuario = new Usuario();
            
            
            Comuna comunaExistente = comunasGuardadas.get(i);
            
            
            usuario.setNombreUsuario(faker.internet().username());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setFechaNacimiento(LocalDate.of(1995, 5, 15));
            usuario.setComuna(comunaExistente);
            
            usuarioRepository.save(usuario);
        }
    } 
}
