package com.example.dbdemo;

import com.example.dbdemo.Config.DataBaseControl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class DbDemoApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(DbDemoApplication.class, args);
        DataBaseControl dbs = new DataBaseControl();
        DataBasePanel dbp = new DataBasePanel(dbs.getNewConnection());
            try {
                dbp.start();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }




}
