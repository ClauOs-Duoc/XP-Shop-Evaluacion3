XP-Shop - Arquitectura de Microservicios
Descripción del proyecto

XP-Shop es una aplicacion desarrollada utilizando una arquitectura basada en microservicios con Spring Boot. El objetivo del proyecto fue separar las distintas funcionalidades del sistema en servicios independientes para facilitar su mantenimiento, organización y escalabilidad.

En esta evaluación se trabajo con los microservicios de Usuario, Producto y Boleta, además de implementar un API Gateway para centralizar las solicitudes y un Servidor Eureka para el descubrimiento de servicios.

Integrantes
Matias Meza
Tomas Gallegos
Claudio Osorio

Tecnologías utilizadas
Java 21
Spring Boot
Spring Web
Spring Data JPA
Spring Cloud Gateway
Netflix Eureka
MySQL
Maven
Swagger / OpenAPI
JUnit
Mockito
GitHub

Microservicios implementados

Bloque_Usuario
Este microservicio permite administrar la información de los usuarios del sistema.
Se implementaron las operaciones CRUD para registrar, consultar, actualizar y eliminar usuarios. Además, se conectó a una base de datos MySQL utilizando Spring Data JPA y quedó registrado en Eureka para ser descubierto por el Gateway.

Bloque_Producto
Este servicio se encarga de la gestión de productos.
Permite crear, listar, modificar y eliminar productos mediante endpoints REST. También utiliza Spring Data JPA para acceder a la base de datos y se registra automáticamente en Eureka.

Bloque_Boleta
Este microservicio administra las boletas generadas por el sistema.
Se desarrollaron los endpoints necesarios para crear y consultar boletas, almacenando la información en MySQL. Al igual que los demás servicios, se registra en Eureka para que pueda ser consumido mediante el Gateway.

API Gateway
Se implementó un API Gateway utilizando Spring Cloud Gateway.
Su función es recibir todas las solicitudes del cliente y redirigirlas automáticamente al microservicio correspondiente, evitando acceder directamente a cada servicio.
También se configuraron las rutas mediante el archivo application.yml utilizando el descubrimiento automático de Eureka.

Eureka Server
Se implementó un servidor Eureka para registrar todos los microservicios.
Cada servicio se conecta automáticamente al iniciar la aplicación, permitiendo que el Gateway encuentre los microservicios sin necesidad de configurar manualmente sus direcciones.

los puertos son los siguientes:
