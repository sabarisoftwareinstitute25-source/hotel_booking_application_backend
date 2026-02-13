package com.hotelbooking.mobileapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * Initializes and verifies database connection on application startup.
 */
@Component
@Order(1)
public class DataSourceInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceInitializer.class);

    private final DataSource dataSource;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    public DataSourceInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
        try {
            logger.info("========================================");
            logger.info("  DATABASE CONNECTION INITIALIZATION");
            logger.info("========================================");
            logger.info("Database URL: {}", datasourceUrl);
            logger.info("Database Username: {}", datasourceUsername);
            
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();
                logger.info("✅ Database connection successful!");
                logger.info("Database Product: {}", metaData.getDatabaseProductName());
                logger.info("Database Version: {}", metaData.getDatabaseProductVersion());
                logger.info("Driver Name: {}", metaData.getDriverName());
                logger.info("Driver Version: {}", metaData.getDriverVersion());
                logger.info("URL: {}", metaData.getURL());
                logger.info("========================================");
            } catch (Exception e) {
                logger.error("❌ Database connection failed!", e);
                logger.error("Please check:");
                logger.error("1. PostgreSQL is running");
                logger.error("2. Database 'hotelbooking-app' exists");
                logger.error("3. Username and password are correct");
                logger.error("4. Connection URL is correct");
                throw new RuntimeException("Database connection failed", e);
            }
        } catch (Exception e) {
            logger.error("Failed to initialize data source", e);
        }
    }
}

