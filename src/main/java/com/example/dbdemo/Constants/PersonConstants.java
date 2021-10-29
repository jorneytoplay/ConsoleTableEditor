package com.example.dbdemo.Constants;

public class PersonConstants {
    public static class PersonSQL {
        public static String FIND_ALL_BY_ID = "SELECT * FROM gamers WHERE id = ? ";
        public static String FIND_ALL = "SELECT * FROM gamers";
        public static String FIND_ALL_BY_AGE = "SELECT * FROM gamers WHERE age = ? ";
        public static String FIND_ALL_BY_NICKNAME = "SELECT * FROM gamers WHERE NICKNAME = ? ";
        public static String FIND_ALL_BY_GAME = "SELECT * FROM gamers WHERE GAME = ? ";
        public static String INSERT="INSERT INTO gamers (nickname, age, game) VALUES (?,?,?)";
        public static String GET_LAST_USER="SELECT * FROM gamers ORDER BY id DESC LIMIT 1";
        public static String DELETE_USER="DELETE FROM gamers WHERE id = ?";
        public static String UPDATE_USER_NICKNAME=" UPDATE gamers SET nickname = (?) WHERE id = (?)";
        public static String UPDATE_USER_AGE=" UPDATE gamers SET age = ? WHERE id = ?";
        public static String UPDATE_USER_GAME=" UPDATE gamers SET game = ? WHERE id = ?";
    }
}
