package ar.com.gepp.examples.services.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class TestConfigurations {

    @Bean
    public DataSource sqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:test-db");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

}