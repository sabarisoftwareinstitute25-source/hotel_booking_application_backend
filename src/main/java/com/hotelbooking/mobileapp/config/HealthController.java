package com.hotelbooking.mobileapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import javax.sql.DataSource;

/**
 * Health check and database connection test controller.
 */
@RestController
@RequestMapping("/api/health")
@CrossOrigin(origins = "*")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * GET /api/health
     * Basic health check endpoint.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Hotel Booking API");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/health/database
     * Test database connection and return connection details.
     */
    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> testDatabase() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Test database connection
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();
                
                response.put("status", "CONNECTED");
                response.put("database", metaData.getDatabaseProductName());
                response.put("version", metaData.getDatabaseProductVersion());
                response.put("url", metaData.getURL());
                response.put("username", metaData.getUserName());
                response.put("driver", metaData.getDriverName());
                response.put("driverVersion", metaData.getDriverVersion());
                
                // Test query
                boolean isValid = connection.isValid(5);
                response.put("connectionValid", isValid);
                
                if (isValid) {
                    response.put("message", "Database connection successful");
                } else {
                    response.put("message", "Database connection invalid");
                }
                
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Database connection failed: " + e.getMessage());
            response.put("error", e.getClass().getName());
            if (e.getCause() != null) {
                response.put("cause", e.getCause().getMessage());
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }

    /**
     * GET /api/health/tables
     * Check if required tables exist in the database.
     */
    @GetMapping("/tables")
    public ResponseEntity<Map<String, Object>> checkTables() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();
                String[] tableTypes = {"TABLE"};
                
                // Check for required tables
                // Match actual JPA @Table names
                String[] requiredTables = {
                    "hotel_vendors",
                    "hotels",
                    "usersaccount",
                    "otps"
                };
                
                Map<String, Boolean> tablesStatus = new HashMap<>();
                boolean allTablesExist = true;
                
                for (String tableName : requiredTables) {
                    try (var rs = metaData.getTables(null, null, tableName, tableTypes)) {
                        boolean exists = rs.next();
                        tablesStatus.put(tableName, exists);
                        if (!exists) {
                            allTablesExist = false;
                        }
                    }
                }
                
                response.put("status", allTablesExist ? "OK" : "MISSING_TABLES");
                response.put("tables", tablesStatus);
                response.put("allTablesExist", allTablesExist);
                
                if (allTablesExist) {
                    response.put("message", "All required tables exist");
                } else {
                    response.put("message", "Some required tables are missing");
                }
                
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Failed to check tables: " + e.getMessage());
            response.put("error", e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

