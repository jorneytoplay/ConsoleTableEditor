package com.example.dbdemo.Config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class DataBaseControl {

    @Bean
    public Connection getNewConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/pro_gamer";
        String user = "postgres";
        String passwd = "root";
        return DriverManager.getConnection(url, user, passwd);
    }

    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.ds.PGSimpleDataSource");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pro_gamer");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");

        return dataSource;
    }

}
