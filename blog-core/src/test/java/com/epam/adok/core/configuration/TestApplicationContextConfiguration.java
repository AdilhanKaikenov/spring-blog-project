package com.epam.adok.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.adok.core")
@EnableJpaRepositories("com.epam.adok.core.repository")
@Import({DataBaseConfiguration.class, MethodSecurityConfig.class})
public class TestApplicationContextConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/blog_db_test");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        return dataSource;
    }
}
