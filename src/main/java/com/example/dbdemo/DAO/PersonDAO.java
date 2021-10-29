package com.example.dbdemo.DAO;

import com.example.dbdemo.Config.DataBaseControl;
import com.example.dbdemo.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.dbdemo.Constants.PersonConstants.PersonSQL.*;

public class PersonDAO<T> implements GenericDAO<T> {
    public Connection connection;
    DataBaseControl dbc = new DataBaseControl();
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(Connection connection) {
        this.connection = connection;
        jdbcTemplate=new JdbcTemplate(dbc.mysqlDataSource());
    }


    @Override
    public T insert(T nickname,T age, T game) {
        jdbcTemplate.update(INSERT,nickname,age,game);
        return (T) jdbcTemplate.queryForObject(
                GET_LAST_USER,
                new Object[]{},
                new BeanPropertyRowMapper(Person.class));
    }

    public T getById(T param) {
        return (T) jdbcTemplate.queryForObject(
                        FIND_ALL_BY_ID,
                new Object[]{param},
                new BeanPropertyRowMapper(Person.class));
    }

    public List getByAge(T param) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_BY_AGE, param);
        return rowParser(rows);
    }

    public List getByNickname(T param) {
       List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_BY_NICKNAME,param);
       return rowParser(rows);
    }

    @Override
    public boolean update(T id,T column,T param) {
        switch ((String)column){
            case "nickname":
                return jdbcTemplate.update(UPDATE_USER_NICKNAME,new Object[] {param,id})==1;
            case "age":
                return jdbcTemplate.update(UPDATE_USER_AGE,new Object[] {param,id})==1;
            case "game":
                return jdbcTemplate.update(UPDATE_USER_GAME,new Object[] {param,id})==1;
        }
        return false;
        }

    @Override
    public boolean delete(T param) {
            return jdbcTemplate.update(DELETE_USER,new Object[] {param}) == 1;
    }

    @Override
    public List getAll() {
        List<Person> personList = jdbcTemplate.query(
                FIND_ALL,
                new BeanPropertyRowMapper(Person.class));

        return personList;
    }


    private List rowParser(List<Map<String, Object>> rows){
        List<Person> personList = new ArrayList<Person>();
        for (Map row : rows) {
            Person obj = new Person();
            obj.setId((Integer) row.get("id"));
            obj.setNickname((String) row.get("nickname"));
            obj.setAge((Integer) row.get("age"));
            personList.add(obj);
        }
        return personList;
    }
}
