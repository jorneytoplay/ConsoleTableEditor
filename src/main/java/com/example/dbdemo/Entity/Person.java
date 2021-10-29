package com.example.dbdemo.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Person {

    @Id
    int id = 0;
    String nickname;
    int age = 0;
    int game=0;


    @Override
    public String toString() {
        return "Gamer{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                '}';
    }
}
