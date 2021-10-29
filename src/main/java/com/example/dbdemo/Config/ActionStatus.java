package com.example.dbdemo.Config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ActionStatus {
    boolean connected;
    boolean paramMode;
    String table=null;
    boolean tableSelect;
}
