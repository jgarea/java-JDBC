# Como concetarse a una base de datos con java-JDBC
[![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white&labelColor=101010)]()
## Tabla de Contenidos
1. [Introducción](#introduccion)

## Introducción 

**JAVA DATABASE CONNECTIVITY (JDBC)**
* API que permite operaciones sobre bases de datos (relacionales) desde Java.
* Es una colección de interfaces Java.
* Cada SGBD implementa un driver de conexión.
* La base de datos se localiza a través de una URL JBDC.
* Disponible tanto con Java SE como con Java/Jakarta EE.

**VERSIONES JDBC**
* JDBC 4.3 (2017 - Java SE 9) (actual)
* Retrocompatible con las versiones anteriores

**DRIVER JDBC**
* Es una implementación de la especificación JDBC para un gestor de base de datos concreto.
    * MySQL y PostgreSQL tienen drivers diferentes.
    * Pueden existir diferentes drivers para un mismo gestor de base de datos (aunque actualmente no es común) (Oracle).

**Tipos de drivers**
1. Bridge: son un puente con otra tecnología, por ejemplo ODBC.
    * Necesidad de otro driver (ODBC).
    * Uso de código nativo (JNI).
2. Native: parte del driver está en Java, y otra parte en código nativo del gestor de bases de datos.
    * Más rápido que el anterior.
    * Las llamadas a métodos JDBC se traducen a llamadas específicas del API de la base de datos.
    * Uso de código nativo (JNI) 
3. Middleware: se utiliza un servidor intermedio, que abstrae la conexión.
    * Puede servir de puerta a múltiples servidores.
    * No necesita utilizar código nativo.
4. Java Pure: se utiliza Java para implementar el protocolo de red de acceso al DBMS.
    * No necesita utilizar código nativo.
    * Conexión más directa, mejor rendimiento
    * Debe ser suministrado por el proveedor del DBMS.

<h1 align=center>
<img src="https://www.sitesbay.com/jdbc/images/types-of-jdbc-drivers.png" width="50%"></img>
</h1>

**DRIVER JDBC COMO UN JAR**
Habitualmente, los drivers se distribuyen como un fichero JAR.
* Para usarlo:
    * **Dependencia maven.**
    * Descargar manualmente y añadir al classpath.

**PRINCIPALES CLASES E INTERFACES DE JDBC**

* DriverManager: esta clase proporciona métodos para registrar y conectarse a un controlador JDBC.
* Connection: esta interfaz representa una conexión con una base de datos y proporciona métodos para enviar consultas y transacciones.
* Statement: esta interfaz representa una sentencia SQL y proporciona métodos para enviarla a la base de datos y recuperar los resultados.
* PreparedStatement: esta clase es una subclase de Statement que permite enviar sentencias SQL precompiladas a la base de datos.
* Callable Statement: esta clase es una subclase de Statement que permite llamar a procedimientos almacenados en la base de datos.
* ResultSet: esta interfaz representa un conjunto de resultados obtenidos de una consulta SQL y proporciona métodos para acceder y recorrer los resultados.
* SQLException: esta clase representa una excepción generada por un error en la conexión o en la ejecución de una consulta SQL.

## Crear base de datos y la tabla con phpMyAdmin
```
CREATE TABLE `concesionario`.`coches` ( `id` INT NOT NULL AUTO_INCREMENT , `texto` TEXT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
```

**[⬆ Volver arriba](#tabla-de-contenidos)**
