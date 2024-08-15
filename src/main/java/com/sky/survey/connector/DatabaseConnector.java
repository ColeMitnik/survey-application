package com.sky.survey.connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseConnector {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);

    private final DataSource dataSource;

    @Autowired
    public DatabaseConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null && !connection.isClosed()) {
                logger.info("Successfully connected to the PostgreSQL database!");
            } else {
                logger.error("Failed to connect to the PostgreSQL database.");
            }
        } catch (SQLException e) {
            logger.error("Error connecting to the database: ", e);
        }
    }
}