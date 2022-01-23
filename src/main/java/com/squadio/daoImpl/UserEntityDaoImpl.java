package com.squadio.daoImpl;

import com.squadio.dao.UserEntityDao;
import com.squadio.entities.UserEntity;
import com.squadio.exception.BadRequestException;
import com.squadio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityDaoImpl extends CrudDaoImpl<UserEntity,Long> implements UserEntityDao {
    private UserRepository repository;
    protected UserEntityDaoImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserEntity getByUserId(String userId) {
        return repository.findByUserId(userId).orElseThrow(() -> new BadRequestException("Invalid user id"));
    }
}
