package com.example.dbdemo.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Person {

    int id = 0;
    String nickname;
    int age = 0;



    @Override
    public String toString() {
        return "Gamer{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                '}';
    }
}
