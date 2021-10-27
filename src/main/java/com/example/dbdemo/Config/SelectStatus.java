package com.example.dbdemo.Config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SelectStatus {
    boolean connected;
    boolean paramMode;
}
