package com.example.dbdemo.DAO;

import java.util.List;

public interface GenericDAO<T> {
    T create();

    // T getByParam(String comm, T param);
    void update(T param);

    void delete(T param);
    List<T> getAll();

}
