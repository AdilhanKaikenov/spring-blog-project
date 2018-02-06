package com.epam.adok.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableScheduling
@EnableJpaRepositories("com.epam.adok.core.repository")
@Import({DataBaseConfiguration.class, SpringAOPConfiguration.class, MethodSecurityConfig.class})
public class RootApplicationContextConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/blog_db");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        return dataSource;
    }

}
