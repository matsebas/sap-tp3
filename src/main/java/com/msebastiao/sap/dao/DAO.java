package com.msebastiao.sap.dao;

import java.util.List;

/**
 * Interface que define las operaciones básicas del patrón Data Access Object (DAO).
 *
 * @param <T> clase del objeto que se va a manipular en la base de datos (ej. Turno, Mecanico, etc.)
 */
public interface DAO<T> {
    void insert(T t) throws Exception;

    T getById(int id) throws Exception;

    List<T> getAll() throws Exception;

    void update(T t) throws Exception;

    void delete(int id) throws Exception;
}
