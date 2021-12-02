package com.example.dbdemo.DAO.Person;

import com.example.dbdemo.Config.DataBaseControl;
import com.example.dbdemo.Config.HibernateSessionFactoryUtil;
import com.example.dbdemo.Entity.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static com.example.dbdemo.Constants.PersonConstants.PersonSQL.GET_LAST_USER;
import static org.hibernate.loader.internal.AliasConstantsHelper.get;


public class PersonDAOHib {
    HibernateSessionFactoryUtil hsfu = new HibernateSessionFactoryUtil();
    EntityManager em;

    public Person insert(String nickname,int age, int game) {
        em = hsfu.getEm();
        em.getTransaction().begin();

        Person person = new Person();
        person.setNickname(nickname);
        person.setAge(age);
        person.setGame(game);
        em.persist(person);
        em.getTransaction().commit();
        return person;
    }

    public Person findById(int id) {
        em = hsfu.getEm();
        Person person = em.find(Person.class, new Integer(id));
        em.detach(person);
        return person;
    }

    public List<Person> getAll(){
        em = hsfu.getEm();
        TypedQuery<Person> namedQuery = em.createNamedQuery("Person.getAll", Person.class);
        return namedQuery.getResultList();
    }

    //TODO
    public boolean delete(int id){
        em = hsfu.getEm();
        try {
            em.getTransaction().begin();
            em.remove(id);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
