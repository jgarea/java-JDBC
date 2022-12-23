# Como concetarse a una base de datos con java-JDBC
[![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white&labelColor=101010)]()
## Tabla de Contenidos
1. [Introducción](#introducción)
2. [Crear base de datos y la tabla con phpMyAdmin](#crear-base-de-datos-y-la-tabla-con-phpmyadmin)
3. [Crear el proyecto con Maven](#crear-el-proyecto-con-maven)
4. [Cómo obtener el driver](#cómo-obtener-el-driver)
5. [Establecer de la conexión con la base de datos](#establecimiento-de-la-conexión-con-la-base-de-datos)
6. [Interfaz statement](#interfaz-statement)
7. [Pasos a realizar para inserción](#pasos-a-realizar-para-inserción)
8. [Pasos a realizar para consultas SELECT](#pasos-a-realizar-para-consultas-SELECT)
9. [Cierre de la base de datos y los objetos](#cierre-de-la-base-de-datos-y-los-objetos)
10. [Código final](#código-final)

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

**[⬆ Volver arriba](#tabla-de-contenidos)**

**PRINCIPALES CLASES E INTERFACES DE JDBC**

* DriverManager: esta clase proporciona métodos para registrar y conectarse a un controlador JDBC.
* Connection: esta interfaz representa una conexión con una base de datos y proporciona métodos para enviar consultas y transacciones.
* Statement: esta interfaz representa una sentencia SQL y proporciona métodos para enviarla a la base de datos y recuperar los resultados.
* PreparedStatement: esta clase es una subclase de Statement que permite enviar sentencias SQL precompiladas a la base de datos.
* Callable Statement: esta clase es una subclase de Statement que permite llamar a procedimientos almacenados en la base de datos.
* ResultSet: esta interfaz representa un conjunto de resultados obtenidos de una consulta SQL y proporciona métodos para acceder y recorrer los resultados.
* SQLException: esta clase representa una excepción generada por un error en la conexión o en la ejecución de una consulta SQL.

**[⬆ Volver arriba](#tabla-de-contenidos)**

## Crear base de datos y la tabla con phpMyAdmin
```sql
CREATE TABLE alumno (

    id_alumno MEDIUMINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    email VARCHAR(320),
    PRIMARY KEY (id_alumno)
);

INSERT INTO alumno (nombre, apellidos, edad, email)
VALUES  ('María', 'López Martínez', 18, NULL),
        ('José', 'García González', 23, "josexyz@gmail.com"),
        ('Ana', "Del Campo Rodríguez", 19, "anukyfield@gmail.com"),
        ('Martín', "Suárez Trevejo", 24, NULL);
```

**[⬆ Volver arriba](#tabla-de-contenidos)**
## Crear el proyecto con Maven
* Maven: Empty project
    * Java 17
    * Group Id
    * Artifact Id

**Fichero POM.xml**
* Añadimos algunos elementos:
    * UTF8, Java 17.
    * Plugins (test, compile, exec…).
    * Dependencias (por ahora JUnit, por si se quieren hacer pruebas).

* Comprobamos
```xml
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.java.mysql</groupId>
    <artifactId>proyecto-base-de-datos</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <properties>

        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>19</maven.compiler.source>
        <maven.compiler.target>19</maven.compiler.target>
        
    </properties>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.java.mysql.proyecto.base.de.datos.ProyectoBaseDeDatos</mainClass>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.2</version>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.10.1</version>
            </plugin>
        </plugins>
    </build>
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.8.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mariadb.jdbc</groupId>
            <artifactId>mariadb-java-client</artifactId>
            <version>2.1.2</version>
        </dependency>
    </dependencies>
</project>
```
**[⬆ Volver arriba](#tabla-de-contenidos)**
## Carga del driver 
* Implementación concreta del estándar JDBC para un SGBD.
* Empaquetado en un JAR.
* Puede existir un JAR para cada versión del SGBD.
* MariaDB y MySQL tienen drivers compatibles.

**[⬆ Volver arriba](#tabla-de-contenidos)**

## CÓMO OBTENER EL DRIVER
Descargarlo de la web de Mysql o mariadb
* https://dev.mysql.com/downloads/connector/j/8.0.html
* **Como dependencia en un proyecto maven**
```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>2.1.2</version>
</dependency>
```
**[⬆ Volver arriba](#tabla-de-contenidos)**
## ESTABLECIMIENTO DE LA CONEXIÓN CON LA BASE DE DATOS
* DriverManager
    * Se trata del responsable de crear la conexión con la base de datos.
    * Utilizará el driver que esté registrado en el classpath y una URL JDBC para crear la conexión.
* Connection
    * Se trata de la conexión abierta entre nuestra aplicación Java y la base de datos.
    * Es la responsable de crear las sentencias SQL que podemos lanzar.
    * Igual que se abre, debe cerrarse una vez que no vaya a usarse.

### URL JDBC
* Es una cadena de conexión a la base de datos.
* Incluye información sobre el tipo de conector, el host donde se
encuentra la base de datos.
* También puede incluir parámetros, user/password…
* Tiene diferentes tramos separados por dos puntos (:).
* La estructura es protocol://[host][/database][?properties]
Siempre comienza por jdbc:
* Le sigue el gestor de base de datos / tipo de driver a usar:
    * Algunos motores de base de datos, como Oracle, han tenido históricamente más de un tipo.
    * Para mysql, sería jdbc:mysql:
* A continuación, encontraríamos la cadena de conexión al host
donde se encuentra el servidor de base de datos. Como prefijo se
usan dos barras //. El puerto por defecto es 3306.
    * Si es en local, sería jdbc:mysql://localhost
    * En remoto, dependería de la url del host. Por ejemplo,
jdbc:mysql://db.mydomain.net:3307 o jdbc:mysql://51.52.53.54
* A continuación, encontraríamos una barra para indicar la base de
datos a la que nos conectamos dentro del servidor
    * Por ejemplo jdbc:mysql://localhost/test
* Por último, y tras usar como separador un interrogante (?)
encontraríamos propiedades de la conexión.
    * Por ejemplo jdbc:mysql://localhost/test?useSSL=false

### NUESTRA URL DE CONEXIÓN
* Conectamos a una base de datos en Docker.
* Por el mapeo de puertos, es como si la tuviéramos en local, en el
puerto 3306.
* Nos conectamos a una base de datos llamada database con un
usuario user y una contraseña password.
* Algunas propiedades:
    * useSSL=false. Por defecto es true. No usamos este mecanismo en local.
    * allowPublicKeyRetrieval=true. Por defecto false. Permite generar una clave pública RSA para la autenticación sha256_password. 

```java
String ulr="""
jdbc:mariadb://localhost/alumnos?allowPublicKeyRetrieval=true&useSSL=false
""";
```

### OBTENER LA CONEXIÓN
DriverManager.getConnection(...)
* Este método tiene varias firmas
    * Solo la URL
    * **URL, user y password**
    * URL y properties

### CERRAR LA CONEXIÓN
Connection.close()
* Una vez que hemos realizado las operaciones con la base de datos
la conexión se puede cerrar.
* Libera recursos tanto en la JVM como en el servidor de bases de
datos.
* Lo trabajaremos con más detenimiento al final del lab.

https://github.com/microsoft/mariadb-connector-j/blob/master/src/test/java/org/mariadb/jdbc/JdbcParserTest.java

```java
package com.java.mysql.proyecto.base.de.datos;

import java.sql.*;
import org.mariadb.jdbc.UrlParser;

/**
 *
 * @author Juan
 */
public class ProyectoBaseDeDatos {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Hello World!");
        /*String url="""
jdbc:mariadb://localhost/cies?allowPublicKeyRetrieval=true&useSSL=false
""";*/
        try {
            UrlParser jdbc = UrlParser.parse("jdbc:mariadb://localhost/alumnos?allowPublicKeyRetrieval=true&useSSL=false");
            //Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbc.toString(), "root", "");

            System.out.println("Creando la base de datos");

            con.close();
        } catch (SQLException ex) {
            System.out.println("SQLException" + ex.getMessage());
            System.out.println("SQLState" + ex.getSQLState());
            System.out.println("ErrorCode" + ex.getErrorCode());
        }
    }
}

```
**[⬆ Volver arriba](#tabla-de-contenidos)**

## INTERFAZ STATEMENT
**java.sql.Statement**
* Encapsula una sentencia SQL.
* Sirve para ejecutarla y recoger el resultado (este se guarda en otro
tipo de objeto).
* Tiene algunos subtipos (que trabajaremos en laboratorios
posteriores de esta serie).
### CÓMO CREAR UN STATEMENT
**conn.createStatement();**
* Se crea a partir de la conexión.
* Diferentes firmas de este método.
* Opciones sobre el objeto que recogerá los resultados.

[Java doc interfaz Statement](https://docs.oracle.com/javase/8/docs/api/java/sql/Statement.html)

```sql
CREATE TABLE alumno (

    id_alumno MEDIUMINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    email VARCHAR(320),
    PRIMARY KEY (id_alumno)
);

INSERT INTO alumno (nombre, apellidos, edad, email)
VALUES  ('María', 'López Martínez', 18, NULL),
        ('José', 'García González', 23, "josexyz@gmail.com"),
        ('Ana', "Del Campo Rodríguez", 19, "anukyfield@gmail.com"),
        ('Martín', "Suárez Trevejo", 24, NULL);
```
**[⬆ Volver arriba](#tabla-de-contenidos)**
## Pasos a realizar para inserción
1. STATEMENT
* Crear un Statement a partir del Connection.
* Este es el responsable de poder ejecutar consultas.
    * DQL: SELECT
    * DML: INSERT, UPDATE, DELETE
```java
Statement stm = con.createStatement();
```
2. EXECUTE
* Statement posee varios métodos para ejecutar consultas.
* Las consultas del DML pueden usar executeUpdate.
    * Recibe como argumento la consulta sql
    * Devuelve el nº de filas afectadas
```java
String sql = "INSERT INTO ALUMNO (nombre,apellidos,edad,email) VALUES('Sergio','Martinez del Campo',25,'serginho@gmail.com')";
int n = stm.executeUpdate(sql);
if (n > 0) {
    System.out.println("Se ha insertado un registro en la base datos");
}
```
3. EJECUTAR Y COMPROBAR

**[⬆ Volver arriba](#tabla-de-contenidos)**
## Pasos a realizar para consultas SELECT
1. STATEMENT
* Podemos crear un Statement nuevo o reutilizar el anterior.
    * ¡OJO al reutilizar Statements con consultas SELECT!
    * No olvidar cerrar los recursos (último vídeo del curso)
```java
Statement stm = con.createStatement();
```

2. EXECUTEQUERY
* Ejecutar la consulta con executeQuery.
* Devuelve un objeto de tipo ResultSet.
    * Encapsula los resultados de la consulta.
    * Ofrece métodos para movernos por las filas resultado, y obtener los datos de cada columna.
    * Se necesita un bucle para recorrerlo.

```java
String sql2 = "SELECT * FROM alumno";
ResultSet rs = stm.executeQuery(sql2);
```
3. PROCESAR RESULTADOS
* Bucle con ResultSet
* `while (rs.next()) { …. }`
    * JDBC sitúa el puntero del ResultSet en la posición anterior al primer resultado.
    * next() nos permite mover el cursor hacia adelante.
    * Devuelve true si al moverse encuentra una fila, o false si ya no quedan filas que procesar.
4. PROCESAR CADA FILA
* Una vez que vamos moviendo el puntero del ResultSet, podemos procesar cada fila.
* Podemos obtener los valores de cada columna con los métodos getXXXX.
* Métodos con diferentes firmas para acceder vía índice o nombre de columna.
* Diferentes métodos para cada tipo de dato.

```java
while (rs.next()) {
    System.out.println(
            String.format("%d. %s %s %d %s",
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getString(5)
            )
    );                
    
}
```
**[⬆ Volver arriba](#tabla-de-contenidos)**


## Cierre de la base de datos y los objetos
* La conexión con base de datos se considera un **objeto pesado**.
* Quiere decir que ocupa bastante memoria.
* Además, consume recursos tanto por parte de Java (en la JVM) como en la parte del servidor de base de datos.
* Por tanto, es necesario cerrar todo aquello que no vayamos a usar más.
### Cierre de elementos
* Suele realizarse en un bloque **finally** (try-catch-**finally**).
    * Se ejecuta tanto si finaliza el bloque try, como si salta al bloque catch por una excepción.
* Se suelen cerrar los elementos en orden inverso al que se han abierto.
* Apertura: Connection → Statement → ResultSet.
* Cierre: ResultSet → Statement → Connection.

* Pasos a seguir:
    * Comprobamos si el elemento es diferente de nulo.
    * Si no lo es, lo cerramos con close() dentro de un try-catch que capture excepciones de tipo SQLException.
    * Le asignamos el valor nulo.

```java
finally {
    if (rs != null) {
        try {
            rs.close();
        } catch (SQLException ex) {}
    }
    if (stm != null) {
        try {
            stm.close();
        } catch (SQLException ex) {}
    }
    if (con != null) {
        try {
            con.close();
        } catch (SQLException ex) {}
    }
}
```

### ÁMBITO DE LOS OBJETOS
* En un bloque try-catch-finally, si declaramos una variable dentro
del bloque try, no será accesible desde finally (está fuera de su
ámbito).
* Hay que declararlas antes del bloque try-catch-finally e inicializar a
null;
**[⬆ Volver arriba](#tabla-de-contenidos)**
## Código final
```java
package com.java.mysql.proyecto.base.de.datos;

import java.sql.*;
import org.mariadb.jdbc.UrlParser;

/**
 *
 * @author Juan
 */
public class ProyectoBaseDeDatos {

    public static void main(String[] args) {
        
       
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            UrlParser jdbc = UrlParser.parse("jdbc:mariadb://localhost/alumnos?allowPublicKeyRetrieval=true&useSSL=false");
            
            con = DriverManager.getConnection(jdbc.toString(), "root", "");

            stm = con.createStatement();
            /*String sql = "INSERT INTO ALUMNO (nombre,apellidos,edad,email) VALUES('Sergio','Martinez del Campo',25,'serginho@gmail.com')";
            int n = stm.executeUpdate(sql);
            if (n > 0) {
                System.out.println("Se ha insertado un registro en la base datos");
            }*/

            System.out.println("Creando la base de datos");

            String sql2 = "SELECT * FROM alumno";
            rs = stm.executeQuery(sql2);
            while (rs.next()) {
                System.out.println(
                        String.format("%d. %s %s %d %s",
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4),
                                rs.getString(5)
                        )
                );

            }

            //con.close();
        } catch (SQLException ex) {
            System.out.println("SQLException" + ex.getMessage());
            System.out.println("SQLState" + ex.getSQLState());
            System.out.println("ErrorCode" + ex.getErrorCode());

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }

            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
}
```

**[⬆ Volver arriba](#tabla-de-contenidos)**
