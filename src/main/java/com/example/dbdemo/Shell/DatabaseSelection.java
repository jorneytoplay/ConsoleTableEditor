package com.example.dbdemo.Shell;

import com.example.dbdemo.DAO.PersonDAO;
import com.example.dbdemo.DataBasePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.validation.constraints.Size;
import java.sql.Connection;

@ShellComponent
public class DatabaseSelection {

    boolean connected;
    @Autowired
    public DatabaseSelection(Connection connection) {
        connected=true;
    }

    public Availability selectDBAvailability() {
        return connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }

    @ShellMethod("Select database.")
    public void selectDB(int choose){
        switch (choose){
            case 1:
                System.out.println("gamers");
                choose=0;
            case 2:
                System.out.println("game");
        }
    }
}
