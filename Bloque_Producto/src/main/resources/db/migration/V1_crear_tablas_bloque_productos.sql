CREATE TABLE categoria (
   idCategoria INT AUTO_INCREMENT PRIMARY KEY,
   nombreCategoria VARCHAR(30) NOT NULL,
   categorias_id INT NOT NULL
);

CREATE TABLE categorias (
   idCategorias INT AUTO_INCREMENT PRIMARY KEY,
   categoria INT NOT NULL,
   producto INT NOT NULL
);

CREATE TABLE imagen (
   idImagen INT AUTO_INCREMENT PRIMARY KEY,
   nombreImagen VARCHAR(30) NOT NULL,
   productoId INT NOT NULL
);

CREATE TABLE marca (
   idMarca INT AUTO_INCREMENT PRIMARY KEY,
   nombreMarca VARCHAR(30) NOT NULL,
   marcas_id INT NOT NULL
);

CREATE TABLE marcas (
   idMarcas INT AUTO_INCREMENT PRIMARY KEY,
   marcas INT NOT NULL,
   productos INT NOT NULL
);

CREATE TABLE producto (
   idProducto INT AUTO_INCREMENT PRIMARY KEY,
   nombreProducto VARCHAR(30) NOT NULL,
   precio DOUBLE(2000000) NOT NULL,
   descripcionProducto VARCHAR(100) NOT NULL,
   stock INT(3) NOT NULL,
   marcas_id INT NOT NULL,
   catagorias_id INT NOT NULL,
   productos_id INT NOT NULL,
   imagen_id INT NOT NULL
);

CREATE TABLE productos (
   idProductos INT AUTO_INCREMENT PRIMARY KEY,
   producto INT NOT NULL,
   id_detalleBoletas INT NOT NULL
);