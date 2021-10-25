package com.example.dbdemo.Config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

}
