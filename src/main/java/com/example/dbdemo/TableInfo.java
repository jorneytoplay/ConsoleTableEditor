package com.example.dbdemo;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableInfo {
    public Connection connection;

    @Autowired
    public TableInfo(Connection connection) {
        this.connection = connection;
    }

    public List columnList(String tableName) {
        List<String> columnNames = new ArrayList<>();
        System.out.println("Table name : " + tableName + ". Column list:");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    columnNames.add(columns.getColumnName(i));
                }

            }
            return columnNames;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
