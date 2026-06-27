package com.XP_Shop.Bloque_Producto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.XP_Shop.Bloque_Producto.model.Categoria;
import com.XP_Shop.Bloque_Producto.model.Categorias;
import com.XP_Shop.Bloque_Producto.model.Imagen;
import com.XP_Shop.Bloque_Producto.model.Marca;
import com.XP_Shop.Bloque_Producto.model.Marcas;
import com.XP_Shop.Bloque_Producto.model.Producto;
import com.XP_Shop.Bloque_Producto.model.Productos;
import com.XP_Shop.Bloque_Producto.repository.CategoriaRepository;
import com.XP_Shop.Bloque_Producto.repository.CategoriasRepository;
import com.XP_Shop.Bloque_Producto.repository.ImagenRepository;
import com.XP_Shop.Bloque_Producto.repository.MarcaRepository;
import com.XP_Shop.Bloque_Producto.repository.MarcasRepository;
import com.XP_Shop.Bloque_Producto.repository.ProductoRepository;
import com.XP_Shop.Bloque_Producto.repository.ProductosRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriasRepository categoriasRepository;
    @Autowired
    private ImagenRepository imagenRepository;
    @Autowired 
    private MarcaRepository marcaRepository;
    @Autowired
    private MarcasRepository marcasRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();

        for (int i = 0; i < 3; i++) {
            Categorias categorias = new Categorias();

            List<Categoria> categorias1 = new ArrayList<>();
            List<Producto> productos1 = new ArrayList<>();

            categorias.setIdCategorias(i);
            categorias.setCategoria(categorias1);
            categorias.setProducto(productos1);

            Categoria categoria = new Categoria();

            categoria.setIdCategoria(i);
            categoria.setNombreCategoria(faker.company().name());
            categoria.setCategorias(categorias);

            categoriaRepository.save(categoria);
        }

        for (int i = 0; i < 3; i++) {
            Categorias categorias = new Categorias();

            List<Categoria> categorias1 = new ArrayList<>();
            List<Producto> productos1 = new ArrayList<>();

            categorias.setIdCategorias(i);
            categorias.setCategoria(categorias1);
            categorias.setProducto(productos1);

            categoriasRepository.save(categorias);
        }

        for (int i = 0; i < 3; i++) {
            Imagen imagen = new Imagen();
            Producto producto = new Producto();

            imagen.setIdImagen(i);
            imagen.setNombreImagen(faker.animal().name());
            imagen.setProducto(producto);

            imagenRepository.save(imagen);
        }

        for (int i = 0; i < 3; i++) {
            Marcas marcas = new Marcas();

            List<Marca> marcas1 = new ArrayList<>();
            List<Producto> productos1 = new ArrayList<>();

            marcas.setIdMarcas(i);
            marcas.setMarcas(marcas1);
            marcas.setProductos(productos1);

            marcasRepository.save(marcas);

            Marca marca = new Marca();

            marca.setIdMarca(i);
            marca.setNombreMarca(faker.company().name());
            marca.setMarcas(marcas);

            marcaRepository.save(marca);
        }

        for (int i = 0; i < 3; i++) {
            Marcas marcas = new Marcas();

            List<Marca> marcas1 = new ArrayList<>();
            List<Producto> productos1 = new ArrayList<>();

            marcas.setIdMarcas(i);
            marcas.setMarcas(marcas1);
            marcas.setProductos(productos1);

            marcasRepository.save(marcas);
        }

        for (int i = 0; i < 3; i++) {
            Producto producto = new Producto();
            Marcas marcas = new Marcas();
            Categorias categorias = new Categorias();

            List<Imagen> imagenes = new ArrayList<>();

            categorias.setIdCategorias(i);
            categorias.setCategoria(null);
            categorias.setProducto(null);

            marcas.setIdMarcas(i);
            marcas.setMarcas(null);
            marcas.setProductos(null);

            producto.setIdProducto(i);
            producto.setNombreProducto(faker.book().title());
            producto.setPrecio(342859.0);
            producto.setDescripcionProducto(faker.famousLastWords().lastWords());
            producto.setStock(631);
            producto.setMarcas(marcas);
            producto.setCategorias(categorias);
            producto.setProductos(null);
            producto.setImagenes(imagenes);

            productoRepository.save(producto);
        }

        for (int i = 0; i < 3; i++) {
            Productos productos = new Productos();

            List<Producto> productos1 = new ArrayList<>();
            List<Integer> detallesBoletas = new ArrayList<>();

            productos.setIdProductos(i);
            productos.setProducto(productos1);
            productos.setId_detalleBoletas(detallesBoletas);

            productosRepository.save(productos);
        }
    } 

}
