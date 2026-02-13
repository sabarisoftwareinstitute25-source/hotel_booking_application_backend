package com.hotelbooking.mobileapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * Hibernate configuration to ensure Jackson is used for JSON/JSONB mapping.
 * This is required for @JdbcTypeCode(SqlTypes.JSON) annotations.
 */
@Configuration
public class HibernateConfig {

    @Autowired
    @Lazy
    private ObjectMapper objectMapper;

    @Autowired
    @Lazy
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @PostConstruct
    public void configureHibernate() {
        // Configure Hibernate to use Jackson for JSON/JSONB mapping
        var properties = entityManagerFactory.getJpaPropertyMap();
        properties.put(AvailableSettings.JSON_FORMAT_MAPPER, objectMapper);
    }
}

