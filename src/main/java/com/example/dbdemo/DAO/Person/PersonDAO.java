package com.example.dbdemo.DAO.Person;

import com.example.dbdemo.Config.DataBaseControl;
import com.example.dbdemo.DAO.GenericDAO;
import com.example.dbdemo.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.dbdemo.Constants.PersonConstants.PersonSQL.*;


public class PersonDAO{  //ИЗБАВИТЬСЯ ОТ ДАННЫЙ БД
    DataBaseControl dbc = new DataBaseControl();
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO() {
        jdbcTemplate=new JdbcTemplate(dbc.mysqlDataSource());
    }

    public Person insert(String nickname,int age, int game) {
        jdbcTemplate.update("INSERT INTO gamers (nickname, age, game) VALUES (?,?,?)",nickname,age,game);//Подумать как исправить named parameter jdbctemplate
        return (Person)jdbcTemplate.queryForObject(
                "SELECT * FROM gamers ORDER BY id DESC LIMIT 1",
                new Object[]{},
                new BeanPropertyRowMapper(Person.class));
    }

    public Person getById(int param) {
        return (Person) jdbcTemplate.queryForObject(
                "SELECT * FROM gamers WHERE id = ? ",
                new Object[]{param},
                new BeanPropertyRowMapper(Person.class));
    }

    public List getByAge(int param) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM gamers WHERE age = ?", param);
        return rowParser(rows);
    }

    public List getByNickname(String param) {
       List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM gamers WHERE NICKNAME = ?",param);
       return rowParser(rows);
    }


    public boolean update(int id,String column,Object param) {
        String update_query = "UPDATE gamers SET " + column + " = (?) WHERE id = (?)";
        try {
            return jdbcTemplate.update(update_query, new Object[]{param, id}) == 1;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean delete(int param) {
            return jdbcTemplate.update("DELETE FROM gamers WHERE id = ?",new Object[] {param}) == 1;
    }

    public List getAll() {
        List<Person> personList = jdbcTemplate.query(
                "SELECT * FROM gamers",
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
