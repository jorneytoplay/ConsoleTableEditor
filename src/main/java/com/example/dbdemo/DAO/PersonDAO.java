package com.example.dbdemo.DAO;

import com.example.dbdemo.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO<T> implements GenericDAO<T>{
    public Connection connection;

    @Autowired
    public PersonDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public T create() {
        return null;
    }

    @Override
    public T getByParam(String comm, T param) {

        String sql = "SELECT * FROM gamers WHERE "+comm+ "= ?";
        Person person = new Person();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) param);
            System.out.println(statement);
            ResultSet rs = statement.executeQuery();
            rs.next();
            person = fillPersonFields(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return (T) person;
    }

    @Override
    public void update(T param) {

    }

    @Override
    public void delete(T param) {

    }


    @Override
    public List getAll() {
        String sql = "SELECT * FROM gamers";
        List<Person> list = new ArrayList<Person>();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                    Person s = new Person();
                s.setId(rs.getInt("id"));
                s.setNickname(rs.getString("nickname"));
                s.setAge(rs.getInt("age"));
                list.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    private Person fillPersonFields(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setAge(resultSet.getInt("age"));
        person.setNickname(resultSet.getString("nickname"));
        return person;
    }
}
