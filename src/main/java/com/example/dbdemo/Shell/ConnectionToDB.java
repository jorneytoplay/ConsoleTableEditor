package com.example.dbdemo.Shell;

import com.example.dbdemo.Config.DataBaseControl;
import com.example.dbdemo.Config.SelectStatus;
import com.example.dbdemo.DAO.PersonDAO;
import com.example.dbdemo.DataBasePanel;
import com.example.dbdemo.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.Connection;
import java.sql.SQLException;

@ShellComponent
public class ConnectionToDB {
    SelectStatus selectStatus;

    @Autowired
    public ConnectionToDB(SelectStatus selectStatus) {
        this.selectStatus = selectStatus;
    }

    @ShellMethod("Connection to data base.")
    public void connect(){
     selectStatus.setConnected(true);
    }


}
