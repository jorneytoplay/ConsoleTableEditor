package com.example.dbdemo.Constants;

public class PersonConstants {
    public static class PersonSQL {
        public static String FIND_ALL_BY_ID = "SELECT * FROM gamers WHERE id = ? ";
        public static String FIND_ALL = "SELECT * FROM gamers";
        public static String FIND_ALL_BY_AGE = "SELECT * FROM gamers WHERE age = ? ";
        public static String FIND_ALL_BY_NICKNAME = "SELECT * FROM gamers WHERE NICKNAME = ? ";
        public static String FIND_ALL_BY_GAME = "SELECT * FROM gamers WHERE GAME = ? ";

    }
}
