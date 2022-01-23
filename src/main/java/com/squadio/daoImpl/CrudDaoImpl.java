package com.squadio.daoImpl;

import com.squadio.dao.CrudDao;
import com.squadio.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class CrudDaoImpl<T, ID> implements CrudDao<T, ID> {
    private final JpaRepository<T,ID> repository;

    protected CrudDaoImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public T getRecordById(ID id) throws RuntimeException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Invalid id " + id));
    }

    @Override
    public T saveRecord(T record) {
        return repository.save(record);
    }

    @Override
    public Collection<T> findAll(){
        return repository.findAll();
    }
}
