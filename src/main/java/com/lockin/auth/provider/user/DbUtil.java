package com.lockin.auth.provider.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.keycloak.component.ComponentModel;

public class DbUtil {
	private final static String CONFIG_KEY_JDBC_DRIVER = "org.postgresql.Driver";
	private final static String CONFIG_KEY_JDBC_URL = "jdbc:postgresql://localhost:5432/vansat_chacao_qa2";
	private final static String CONFIG_KEY_DB_USERNAME = "vansat";
	private final static String CONFIG_KEY_DB_PASSWORD = "vansat123";
	

    public static Connection getConnection(ComponentModel config) throws SQLException{
        String driverClass = config.get("driver");
        try {
            Class.forName(driverClass);
        }
        catch(ClassNotFoundException nfe) {
            throw new RuntimeException("Invalid JDBC driver: " + driverClass + ". Please check if your driver if properly installed");
        }
        
        return DriverManager.getConnection(config.get("url"),
          config.get("username"),
          config.get("password"));
    }
}