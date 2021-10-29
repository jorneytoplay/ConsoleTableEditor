package com.example.dbdemo.Shell;

import com.example.dbdemo.Config.ActionStatus;
import com.example.dbdemo.DAO.PersonDAO;
import com.example.dbdemo.Entity.Person;
import com.example.dbdemo.TableInfo;
import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;


import static com.example.dbdemo.Decoration.AnsiColor.ANSI_GREEN;
import static com.example.dbdemo.Decoration.AnsiColor.ANSI_RESET;
import static java.util.Optional.ofNullable;

@ShellComponent
public class SelectCommand {
    Connection connection;
    ActionStatus actionStatus;
    Scanner sc = new Scanner(System.in);
    TableInfo tableInfo;
    PersonDAO personDAO;

    @Autowired
    public SelectCommand(Connection connection, ActionStatus actionStatus) {
        this.connection = connection;
        tableInfo = new TableInfo(connection);
        personDAO = new PersonDAO(connection);
        this.actionStatus = actionStatus;
    }

    @Component
    class CustomPromptProvider implements PromptProvider{
        @Override
        public AttributedString getPrompt() {
            String tableName;
            if(actionStatus.isTableSelect()){
                tableName=actionStatus.getTable();
            } else {
                tableName="No table selected";
            }
            //TODO
            // String tableName = ofNullable(actionStatus.isTableSelect())
            //         .map(actionStatus.getTable())
            //         .orElse("No table selected");
            // return new AttributedString(String.format(ANSI_GREEN+"table (%s):",ANSI_RESET,tableName))
            return new AttributedString(String.format("table (%s):",tableName));
        }
    }

    @ShellMethodAvailability
    public Availability availabilitySelectCommand()  {
        return actionStatus.isConnected()
                ? Availability.available()
                : Availability.unavailable("You are not connected. Pls enter 'connect'");
    }

    @ShellMethodAvailability({"find-all","find-param"})
    public Availability selectAvailability() {
        return actionStatus.isTableSelect()
                ? Availability.available()
                : Availability.unavailable("Select tables!");
    }

    public Availability paramAvailability() {
        return actionStatus.isParamMode()
                ? Availability.available()
                : Availability.unavailable("Select search by parameter!");
    }

    @ShellMethod("Select database.")
    public void table(int choose) {
        if (choose == 1) {
            actionStatus.setTableSelect(true);
            actionStatus.setTable("gamers");
        } else if (choose == 2) {
            actionStatus.setTableSelect(true);
            actionStatus.setTable("game");
        }
    }

    @ShellMethod("Edit table")
    public void update(){
        int choose;
        int id;
        System.out.println("Enter user ID:");
        id=sc.nextInt();
        System.out.println("What would you like to update?(Select request number)\n1.nickname\n2.age\n3.game");
        choose=sc.nextInt();
        while (true) {

        if(choose==1) {
            System.out.println("Enter any nickname:");
            String nickname = sc.next();
            resultChecker(personDAO.update(id,"nickname",nickname),"update");
            return;
        }
        else if(choose==2){
            System.out.println("Enter any age:");
            int age=sc.nextInt();
            resultChecker(personDAO.update(id,"age",age),"update");
            return;
            }
        else if(choose==3){
            System.out.println("Enter any game:\n1.Dota 2\n2.CS GO\n3.Hearthstone");
            int game=sc.nextInt();
            resultChecker(personDAO.update(id,"game",game),"update");
            return;
        }
        else {
            System.out.println("Request is invalid");
        }
        }

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
        actionStatus.setParamMode(true);
        List<String> columns = tableInfo.columnList(actionStatus.getTable());
        int count = 1;
        for (String column : columns) {
            System.out.println(count + "." + column);
            ++count;
        }
    }

    @ShellMethod("Insert into table")
    public void insert(){
        String nickname;
        int age;
        int game;
        if(actionStatus.getTable().equals("gamers")){
            System.out.println("Enter gamer information\nEnter nickname:");
            nickname=sc.nextLine();
            System.out.println("Enter age:");
            age=sc.nextInt();
            System.out.println("Enter game:\n1.Dota 2\n2.CS GO\n3.Hearthstone");
            game=sc.nextInt();
            System.out.println(personDAO.insert(nickname,age,game));
        }
    }


    @ShellMethod("Delete data")
    public void delete() {
        int id;
        if (actionStatus.getTable().equals("gamers")) {
            System.out.println("Please enter id:");
            id = sc.nextInt();
            resultChecker(personDAO.delete(id),"delete");
        }
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
            default:
                actionStatus.setParamMode(false);
        }
    }

    public void resultChecker(boolean result,String move){
        if(result){
            System.out.println("User "+move+" successfully");
        }
        else {
            System.out.println("Failed to "+move+" user");
        }
    }


}

