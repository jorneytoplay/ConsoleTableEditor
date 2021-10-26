package com.example.dbdemo.DAO;

import com.example.dbdemo.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Lombok;

import static com.example.dbdemo.Constants.PersonConstants.PersonSQL.*;

public class PersonDAO<T> implements GenericDAO<T> {
    public Connection connection;

    @Autowired
    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    //TODO
    @Override
    public T create() {
        return null;
    }

    public T getById(T param) {

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ID)) {
            statement.setInt(1, (Integer) param);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Person person = fillPersonFields(rs);
            return (T) person;
        } catch (Exception e) {
            System.out.println(e);
        }
        return (T) "Sorry not found user";
    }

    public List getByAge(T param) {
        List<Person> list = new ArrayList<Person>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_AGE)) {
            statement.setInt(1, (Integer) param);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Person person = fillPersonFields(rs);
                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List getByNickname(T param) {
        List<Person> list = new ArrayList<Person>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_NICKNAME)) {
            statement.setString(1, (String) param);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Person person = fillPersonFields(rs);
                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public void update(T param) {

    }

    @Override
    public void delete(T param) {

    }

    @Override
    public List getAll() {
        List<Person> list = new ArrayList<Person>();
        try (PreparedStatement stm = connection.prepareStatement(FIND_ALL)) {
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
