package com.squadio.dao;

import com.squadio.exception.NotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface CrudDao<T,ID> {
    Optional<T> findById(ID id);

    T getRecordById(ID id) throws NotFoundException;

    T saveRecord(T record);

    Collection<T> findAll();

}
