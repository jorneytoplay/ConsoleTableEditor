package com.example.dbdemo.Entity;

public class Person {
    int id = 0;
    String nickname;
    int age=0;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Gamer{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                '}';
    }
}
