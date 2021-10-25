package com.example.dbdemo;

import com.example.dbdemo.DAO.PersonDAO;
import com.example.dbdemo.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class DataBasePanel {
    public Connection connection;
    int choose=0;
    Scanner sc = new Scanner(System.in);
    @Autowired
    public DataBasePanel(Connection connection) {
        this.connection = connection;
    }

    public void start() throws SQLException {

        while (true){
            System.out.println("Hello, chose table:\n1.Gamers list\n2.Game list");
            choose=sc.nextInt();
            PersonDAO personDAO = new PersonDAO(connection);
            switch (choose){
                case 1:
                    action("gamers");
                case 2:
                    action("game");
            }
        }
    //    System.out.println(personDAO.getByPK(1));
    }
    public void action(String table){
        TableInfo tableInfo = new TableInfo(connection);
        if(table.equals("gamers")){
            PersonDAO personDAO = new PersonDAO(connection);
            while (true){
                System.out.println("What you want in Gamers list\n1.FindAll\n2.Find< parameter >\n3.Go back");
                choose=sc.nextInt();
                switch (choose){
                    case 1:
                        List<Person> personList = personDAO.getAll();
                        for(Person gamer : personList){
                            System.out.println(gamer);
                        }
                        choose=0;
                    case 2:
                        List<String> columns = tableInfo.columnList(table);
                        int count=1;
                        for(String column : columns){
                            System.out.println(count+"."+column);
                            ++count;
                        }
                        choose=sc.nextInt();
                                switch (choose){
                                    case 1:
                                            System.out.println("Please enter id:");
                                            choose=sc.nextInt();
                                        System.out.println(personDAO.getByParam("id",choose));
                                }
                   // case 3:
                     //   return;
                }
            }
        }
        else if(table.equals("Game list")){

        }
    }
}
