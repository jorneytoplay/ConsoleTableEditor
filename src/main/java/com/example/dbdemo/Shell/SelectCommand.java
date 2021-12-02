package com.example.dbdemo.Shell;

import com.example.dbdemo.Config.ActionStatus;
import com.example.dbdemo.DAO.Person.PersonDAO;
import com.example.dbdemo.DAO.Person.PersonDAOHib;
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
import org.apache.log4j.Logger;


/**
 * The class works completely with dependency Spring Shell
 * This class fully implements CLI interface
 */
@ShellComponent
public class SelectCommand {
    /**
     * Connection - database connection
     * ActionStatus - user selection triggers (like whether he selected a table and which table he chose)
     * TableInfo - output of sub-entities in the table
     * PersonDAO - abstract interface for working with the table of users (gamers)
     */
    Connection connection;
    ActionStatus actionStatus;
    Scanner sc = new Scanner(System.in);
    TableInfo tableInfo;
    PersonDAO personDAO;
    PersonDAOHib pdh;
    private static final Logger logger = Logger.getLogger(
            SelectCommand.class);

    @Autowired
    public SelectCommand(Connection connection, ActionStatus actionStatus) {
        this.connection = connection;
        tableInfo = new TableInfo(connection);
        personDAO = new PersonDAO();
        pdh = new PersonDAOHib();
        this.actionStatus = actionStatus;
        }

    /**
     * This method modifies the console and creates a trigger that responds to table selection.
     */
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

    /**
     * Limiting all actions until the user connects to the database
     * @return nothing if the user connecting to db or warning that the connection not established
     */
    @ShellMethodAvailability
    public Availability availabilitySelectCommand()  {
        return actionStatus.isConnected()
                ? Availability.available()
                : Availability.unavailable("You are not connected. Pls enter 'connect'");
    }

    /**
     * Restrictions on working with tables until the user selects a table
     * @return nothing if the user selected a table or warning that the table is not selected
     */
    @ShellMethodAvailability({"find-all","find-param"})
    public Availability selectAvailability() {
        return actionStatus.isTableSelect()
                ? Availability.available()
                : Availability.unavailable("Select tables!");
    }


    /**
     * This method is called when the user enters "table" and table number into the console.
     * This method is needed to establish the selection of a table.
     * @param choose - table number
     *              actionStatus.setTableSelect - table selection trigger is activated
     *              actionStatus.setTable - sets table selection
     */
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

    /**
     * This method is called when the user enters "update"
     * This method is needed to change the data of the table, the user selects the column and sets the value himself (the row occurs by ID)
     */
    @ShellMethod("Edit table")
    public void update(){
        int choose;
        int id;
        try {
            System.out.println("Enter user ID:");
            id=sc.nextInt();
            System.out.println("What would you like to update?(Select request number)\n1.nickname\n2.age\n3.game");
            choose=sc.nextInt();
            while (true) {

                if(choose==1) {
                    System.out.println("Enter any nickname:");
                    String nickname = sc.next();
                    resultChecker(personDAO.update(id,"nickname",nickname),"update");
                    logger.info("Update complete");
                    return;
                }
                else if(choose==2){
                    System.out.println("Enter any age:");
                    int age=sc.nextInt();
                    resultChecker(personDAO.update(id,"age",age),"update");
                    logger.info("Update complete");
                    return;
                }
                else if(choose==3){
                    System.out.println("Enter any game:\n1.Dota 2\n2.CS GO\n3.Hearthstone");
                    int game=sc.nextInt();
                    resultChecker(personDAO.update(id,"game",game),"update");
                    logger.info("Update complete");
                    return;
                }
                else {
                    System.out.println("Request is invalid");
                }
            }
        }
        catch (Exception e){
            e.getMessage();
        }


    }

    /**
     * This method is called when the user enters "findAll"
     * This method displays the entire contents of the table.
     */
    @ShellMethod("Selection find all")
    public void findAll() {
        try {
            List<Person> personList = pdh.getAll();
            for (Person p : personList) {
                System.out.println(p);
            }
            logger.info("FindAll complete");
        }
        catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }

    }

    /**
     * This method is called when the user enters "findParam"
     * This method is needed for the "parameter" command to work (displays the contents of the table by parameters)
     * This method displays a list of parameters of a preselected table
     *                  actionStatus.setParamMode - trigger that the user has seen the list of table parameters
     *
     */
    @ShellMethod("Selection find by parameter")
    public void findParam() {
        try {
            actionStatus.setParamMode(true);
            List<String> columns = tableInfo.columnList(actionStatus.getTable());
            int count = 1;
            for (String column : columns) {
                System.out.println(count + "." + column);
                ++count;
            }
            param(count);
            logger.info("Find-param complete");
        }
        catch (Exception e){
            e.getMessage();
        }

    }

    /**
     * This method is called when the user enters "insert"
     * In this method the user enters the data he wants to insert into the preselected table
     * If successful, it displays the details of the added information.
     */
    @ShellMethod("Insert into table")
    public void insert(){
        String nickname;
        int age;
        int game;
        try {
            if(actionStatus.getTable().equals("gamers")){
                System.out.println("Enter gamer information\nEnter nickname:");
                nickname=sc.nextLine();
                System.out.println("Enter age:");
                age=sc.nextInt();
                System.out.println("Enter game:\n1.Dota 2\n2.CS GO\n3.Hearthstone");
                game=sc.nextInt();
                System.out.println(pdh.insert(nickname,age,game));
            }
            logger.info("Insert complete");
        }
        catch (Exception e){
            e.getMessage();
        }

    }

    /**
     * This method is called when the user enters "delete"
     * This method allows the user to delete data in a preselected table (the row is selected by ID)
     * On success, the user will be told that the data has been deleted
     */
    @ShellMethod("Delete data")
    public void delete() {
        int id;
        try {
            if (actionStatus.getTable().equals("gamers")) {
                System.out.println("Please enter id:");
                id = sc.nextInt();
                resultChecker(pdh.delete(id),"delete");
            }
            logger.info("Delete complete");
        }
        catch (Exception e){
            e.getMessage();
        }

    }

    /**
     * In this method, the user can display all information about the selected parameter and its value. In a pre-selected table.
     */

    public void param(int count) {
        int chooseInt = 0;
        try {
            while (true){
                chooseInt=sc.nextInt();
                if(chooseInt>0 && chooseInt<count){
                    break;
                }
            }
            List<Person> personList;
            String chooseStr;
            switch (chooseInt) {
                case 1:
                    System.out.println("Please enter id:");
                    chooseInt = sc.nextInt();
                    Person person = pdh.findById(chooseInt);
                    System.out.println(person.toString());
                    break;
                case 2:
                    System.out.println("Please enter nickname:");
                    chooseStr = sc.next();
                    personList = personDAO.getByNickname(chooseStr);
                    for (Person gamer : personList) {
                        System.out.println(gamer);
                    }
                    break;
                case 3:
                    System.out.println("Please enter age:");
                    chooseInt = sc.nextInt();
                    personList = personDAO.getByAge(chooseInt);
                    for (Person gamer : personList) {
                        System.out.println(gamer);
                    }
                    break;
                default:
                    actionStatus.setParamMode(false);
            }
            logger.info("Find by param complete");
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * This method is called by the delete or insert methods to test for success or failure.
     * @param result - boolean containing good or bad luck
     * @param move - the action that the user performed
     */
    public void resultChecker(boolean result,String move){
        if(result){
            System.out.println("User "+move+" successfully");
        }
        else {
            System.out.println("Failed to "+move+" user");
        }
    }


}

