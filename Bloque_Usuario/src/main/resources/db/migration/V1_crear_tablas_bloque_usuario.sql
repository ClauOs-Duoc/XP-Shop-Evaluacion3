CREATE TABLE region (
    idRegion INT AUTO_INCREMENT PRIMARY KEY,
    nombreRegion VARCHAR(40) NOT NULL
);

CREATE TABLE comuna (
    idComuna INT AUTO_INCREMENT PRIMARY KEY,
    nombreComuna VARCHAR(40) NOT NULL,
    region_Id INT NOT NULL,
    CONSTRAINT fk_comuna_region FOREIGN KEY (region_Id) REFERENCES region(idRegion)
);

CREATE TABLE usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombreUsuario VARCHAR(40) NOT NULL,
    correo VARCHAR(40) NOT NULL UNIQUE,
    fechaNacimiento DATE NOT NULL,
    comuna_Id INT NOT NULL,
    CONSTRAINT fk_usuario_comuna FOREIGN KEY (comuna_Id) REFERENCES comuna(idComuna)
);