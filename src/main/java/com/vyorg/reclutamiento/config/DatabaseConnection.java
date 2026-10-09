package main.java.com.vyorg.reclutamiento.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(Credentials.URL_DB, Credentials.USER_DB, Credentials.PASS_DB);
    }
}
