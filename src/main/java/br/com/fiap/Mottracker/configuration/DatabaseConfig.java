package br.com.fiap.Mottracker.configuration;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL = "jdbc:h2:mem:cereaisdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "rm558263";
    private static final String PASSWORD = "071005";

    static {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .load();
        flyway.migrate();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
