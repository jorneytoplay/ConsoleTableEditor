package com.example.dbdemo.Shell;

public class CommandSelection {
    String choose;
    public CommandSelection(String choose) {
        this.choose=choose;
        System.out.println("What you want in Gamers list\n1.FindAll\n2.Find< parameter >\n3.Go back");
    }

}
