package com.example.dbdemo.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name = "gamers")
@NamedQuery(name = "Person.getAll", query = "SELECT p from Person p")
@NamedQuery(name = "Person.getByNickname", query = "SELECT t FROM Person t where t.nickname = :value")
@NamedQuery(name = "Person.getByAge", query = "SELECT t FROM Person t where t.age = :value")
@NamedQuery(name = "Person.getByGame", query = "SELECT t FROM Person t where t.game = :value")
@NamedQuery(name = "Person.delete", query = "DELETE FROM Person t where t.id = :value")
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
