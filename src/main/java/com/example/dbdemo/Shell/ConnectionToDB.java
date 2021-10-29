package com.example.dbdemo.Shell;

import com.example.dbdemo.Config.ActionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ConnectionToDB {
    ActionStatus actionStatus;

    @Autowired
    public ConnectionToDB(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    @ShellMethod("Connection to data base.")
    public void connect(){
     actionStatus.setConnected(true);
    }


}
