package com.example.dbdemo.DAO.Person;

import com.example.dbdemo.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.List;


@Component
@Transactional

public class PersonDAOHib {

    @PersistenceContext
    EntityManager em;


    public Person insert(Person person) {
        //em = hsfu.getEm();
        em.getTransaction().begin();

        em.persist(person);
        em.getTransaction().commit();
        return person;
    }

    public Person findById(int id) {
        //em = hsfu.getEm();

        Person person = em.find(Person.class, new Integer(id));
        em.detach(person);
        return person;
    }


    public List<Person> getAll() {
        //em = hsfu.getEm();
        TypedQuery<Person> namedQuery = em.createNamedQuery("Person.getAll", Person.class);
        return namedQuery.getResultList();
    }

    public List<Person> getByNickname(String param) {
        //em = hsfu.getEm();
        TypedQuery<Person> namedQuery = em.createNamedQuery("Person.getByNickname", Person.class);
        namedQuery.setParameter("value", param);
        return namedQuery.getResultList();
    }

    public List<Person> getByAge(int param) {
        //em = hsfu.getEm();
        TypedQuery<Person> namedQuery = em.createNamedQuery("Person.getByAge", Person.class);
        namedQuery.setParameter("value", param);
        return namedQuery.getResultList();
    }

    public List<Person> getByGame(int param) {
        //em = hsfu.getEm();
        TypedQuery<Person> namedQuery = em.createNamedQuery("Person.getByGame", Person.class);
        namedQuery.setParameter("value", param);
        return namedQuery.getResultList();
    }

    public boolean delete(int param) {
        //em = hsfu.getEm();
        try {
            Person person = findById(param);
            System.out.println(person);
            em.remove(person);

            em.flush();
            em.clear();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
