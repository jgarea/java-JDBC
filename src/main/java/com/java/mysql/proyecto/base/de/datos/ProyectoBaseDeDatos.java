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
