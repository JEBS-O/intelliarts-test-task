package com.expenses.services;

import java.util.List;

public interface SimpleService<T> {
    List<T> getAll();
    T getById(Long id);
    void save(T t);
    T delete(T t);
}
