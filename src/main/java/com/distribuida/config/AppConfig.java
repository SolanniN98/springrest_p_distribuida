package com.distribuida.config;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.distribuida.service")
@EnableTransactionManagement
public class AppConfig {

    private JpaVendorAdapter jpaVendorAdapter(){

     var jpaVendorAdapter = new HibernateJpaVendorAdapter();
     jpaVendorAdapter.setShowSql(true);
     jpaVendorAdapter.setGenerateDdl(true);
     return jpaVendorAdapter;
    }

    @Bean
    public DataSource dataSource(){
        var datasource = new HikariDataSource();
        datasource.setUsername("so");
        datasource.setPassword("");
        datasource.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        datasource.setMinimumIdle(2);
        datasource.setMaximumPoolSize(5);
        return datasource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        return properties;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds){
        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.distribuida.db");
        emf.setDataSource(ds);
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setJpaProperties(additionalProperties());

        return emf;
    }
}
