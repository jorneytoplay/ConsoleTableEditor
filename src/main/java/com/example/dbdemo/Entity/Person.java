package com.example.dbdemo.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name = "gamers")
@NamedQuery(name = "Person.getAll", query = "SELECT p from Person p")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "nickname")
    String nickname;
    @Column(name = "age")
    int age;
    @Column(name = "game")
    int game;


    @Override
    public String toString() {
        return "Gamer{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                '}';
    }
}
