package com.example.dbdemo.Config;

import com.example.dbdemo.Entity.Person;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@Configuration
public class HibernateSessionFactoryUtil {


    @Bean
    public EntityManagerFactory entityManagerFactory(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.baeldung.pro_gamer");
        return emf;

    }
}