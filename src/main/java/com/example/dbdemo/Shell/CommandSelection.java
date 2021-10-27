package com.example.dbdemo.Shell;

import com.example.dbdemo.DAO.PersonDAO;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.Connection;

public class CommandSelection {
    String choose;
    public CommandSelection(String choose) {
        this.choose=choose;
        System.out.println("What you want in Gamers list\n1.FindAll\n2.Find< parameter >\n3.Go back");
    }

}
