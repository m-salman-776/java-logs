package com.wsobserver.repository.dbInterfaces;

import java.util.List;
import java.util.Optional;

public interface DataInterface <T> {
    T getById(long id);
    List<T> getAll();
    void insert(T t);
    void update(T t,String []param);
    void delete(T t);
}
