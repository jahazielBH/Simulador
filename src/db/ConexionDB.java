/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author jahazielBH
 */
public class ConexionDB {

    private static ConexionDB instance = null;
    private Connection connection;

    private ConexionDB() {
        String urldb = "jdbc:postgresql://172.17.0.4:5432/safe";
        String username = "postgres";
        String password = "1999";
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(urldb, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.print("Failied connection: " + ex.getMessage());
        }
        System.out.print("Successful connection\n");
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConexionDB getInstace() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConexionDB();
        }
        return instance;
    }
}
