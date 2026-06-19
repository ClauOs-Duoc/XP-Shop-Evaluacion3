package com.XP_Shop.Bloque_Producto;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.XP_Shop.Bloque_Producto.controller.CategoriaController;
import com.XP_Shop.Bloque_Producto.controller.CategoriasController;
import com.XP_Shop.Bloque_Producto.controller.ImagenController;
import com.XP_Shop.Bloque_Producto.controller.MarcaController;
import com.XP_Shop.Bloque_Producto.controller.MarcasController;
import com.XP_Shop.Bloque_Producto.controller.ProductoController;
import com.XP_Shop.Bloque_Producto.controller.ProductosController;
import com.XP_Shop.Bloque_Producto.model.Categoria;
import com.XP_Shop.Bloque_Producto.model.Categorias;
import com.XP_Shop.Bloque_Producto.model.Imagen;
import com.XP_Shop.Bloque_Producto.model.Marca;
import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.model.Producto;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private CategoriaController categoriaController;
    @Autowired
    private CategoriasController categoriasController;
    @Autowired
    private ImagenController imagenController;
    @Autowired 
    private MarcaController marcaController;
    @Autowired
    private MarcasController marcasController;
    @Autowired
    private ProductoController productoController;
    @Autowired
    private ProductosController productosController;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(i);
            categoria.setNombreCategoria(faker.company().name());
            categoria.setCategorias(null);
        }

        for (int i = 0; i < 3; i++) {
            Categorias categorias = new Categorias();
            categorias.setIdCategorias(i);
            categorias.setCategoria(null);
            categorias.setProducto(null);
        }

        for (int i = 0; i < 3; i++) {
            Imagen imagen = new Imagen();
            Producto producto = new Producto();
            imagen.setIdImagen(i);
            imagen.setNombreImagen(faker.animal().name());
            imagen.setProducto(producto);
        }

        for (int i = 0; i < 3; i++) {
            Marca marca = new Marca();
            marca.setIdMarca(i);
            marca.setNombreMarca(faker.company().name());
            marca.setMarcas(null);
        }

        for (int i = 0; i < 3; i++) {
            Marcas marcas = new Marcas();
            marcas.setIdMarcas(i);
            marcas.setMarcas(null);
            marcas.setProductos(null);
        }

        for (int i = 0; i < 3; i++) {
            Producto producto = new Producto();
            producto.setIdProducto(i);
            producto.setNombreProducto(faker.book().title());
        }
    } 

}
