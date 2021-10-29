package com.example.dbdemo.DAO;

import java.util.List;

public interface GenericDAO<T> {
    T insert(T first,T second, T third);

    // T getByParam(String comm, T param);
    boolean update(T id,T first,T second);

    boolean delete(T param);
    List<T> getAll();

}
