package com.example.dbdemo.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table ( name = "gamers")
public class Person {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int id = 0;
    @Column(name = "nickname")
    String nickname;
    @Column(name = "age")
    int age = 0;
    @Column(name = "game")
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
