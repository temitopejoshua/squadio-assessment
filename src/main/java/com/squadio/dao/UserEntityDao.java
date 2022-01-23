package com.squadio.dao;

import com.squadio.entities.UserEntity;

import java.util.Optional;

public interface UserEntityDao extends CrudDao<UserEntity,Long>{
    Optional<UserEntity> findByUsername(String username);
    UserEntity getByUserId(String userId);

}
