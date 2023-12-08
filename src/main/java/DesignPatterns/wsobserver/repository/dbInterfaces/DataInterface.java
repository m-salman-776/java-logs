package DesignPatterns.wsobserver.repository.dbInterfaces;

import java.util.List;

public interface DataInterface <T> {
    T getById(long id);
    List<T> getAll();
    void insert(T t);
    void update(T t,String []param);
    void delete(T t);

    int getSize();
}
