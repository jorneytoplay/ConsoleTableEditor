package com.example.dbdemo.DAO.Person;

import com.example.dbdemo.Config.DataBaseControl;
import com.example.dbdemo.DAO.GenericDAO;
import com.example.dbdemo.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.dbdemo.Constants.PersonConstants.PersonSQL.*;

@Deprecated(since = "0.1",forRemoval = true)
public class PersonDAOSimpleJdbc<T> implements GenericDAO<T> {
    public Connection connection;
    DataBaseControl dbc = new DataBaseControl();
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Autowired
    public PersonDAOSimpleJdbc(Connection connection) {
        this.connection = connection;
        jdbcTemplate=new JdbcTemplate(dbc.mysqlDataSource());
    }


    @Override
    public T insert(T nickname,T age, T game) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, (String) nickname);
            statement.setInt(2, (Integer) age);
            statement.setInt(3, (Integer) game);
            statement.executeUpdate();
            try(PreparedStatement getId = connection.prepareStatement(GET_LAST_USER)){
                ResultSet rs = getId.executeQuery();
                rs.next();
                int id = rs.getInt("id");

                return (T) ("Gamer{" +
                                        "id=" + id +
                                        ", nickname='" + nickname + '\'' +
                                        ", age=" + age +
                                        '}');
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return (T) "Failed to add user";
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
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_AGE)) {
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
    public boolean update(T id,T column,T param) {
return false;
    }

    @Override
    public boolean delete(T param) {
return false;
    }

    @Override
    public List getAll() {
        List<Person> personList = new ArrayList<Person>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL);
        for (Map row : rows) {
            Person obj = new Person();
            obj.setId((Integer) row.get("id"));
            obj.setNickname((String) row.get("nickname"));
            obj.setAge((Integer) row.get("age"));
            personList.add(obj);
        }
        return personList;
    }


    private Person fillPersonFields(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setAge(resultSet.getInt("age"));
        person.setNickname(resultSet.getString("nickname"));
        person.setGame(resultSet.getInt("game"));
        return person;
    }
}
