package com.example.dbdemo.Shell;

import com.example.dbdemo.Config.SelectStatus;
import com.example.dbdemo.DAO.PersonDAO;
import com.example.dbdemo.Entity.Person;
import com.example.dbdemo.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

@ShellComponent
public class SelectCommand {
    Connection connection;
    SelectStatus selectStatus;
    Scanner sc = new Scanner(System.in);
    TableInfo tableInfo;
    PersonDAO personDAO;

    @Autowired
    public SelectCommand(Connection connection,SelectStatus selectStatus) {
        this.connection = connection;
        tableInfo = new TableInfo(connection);
        personDAO = new PersonDAO(connection);
        this.selectStatus = selectStatus;
    }

    @ShellMethod("Selection find all")
    public void findAll() {
        List<Person> personList = personDAO.getAll();
        for (Person gamer : personList) {
            System.out.println(gamer);
        }
    }

    @ShellMethod("Selection find by parameter")
    public void findParam() {
        selectStatus.setParamMode(true);
        List<String> columns = tableInfo.columnList("gamers");
        int count = 1;
        for (String column : columns) {
            System.out.println(count + "." + column);
            ++count;
        }
    }

    public Availability paramAvailability() {
        return selectStatus.isParamMode()
                ? Availability.available()
                : Availability.unavailable("Select search by parameter!");
    }

    @ShellMethodAvailability()
    public Availability connectAvailability() {
        return selectStatus.isConnected()
                ? Availability.available()
                : Availability.unavailable("You are not connected. Pls enter 'connect'");
    }

    @ShellMethod("Selection parameter")
    public void param(int choose) {
        List<Person> personList;
        int chooseInt;
        String chooseStr;
        switch (choose) {
            case 1:
                System.out.println("Please enter id:");
                chooseInt = sc.nextInt();
                System.out.println(personDAO.getById(chooseInt));
            case 2:
                System.out.println("Please enter nickname:");
                chooseStr = sc.next();
                personList = personDAO.getByNickname(chooseStr);
                for (Person gamer : personList) {
                    System.out.println(gamer);
                }
            case 3:
                System.out.println("Please enter age:");
                chooseInt = sc.nextInt();
                personList = personDAO.getByAge(chooseInt);
                for (Person gamer : personList) {
                    System.out.println(gamer);
                }
        }
    }

}

