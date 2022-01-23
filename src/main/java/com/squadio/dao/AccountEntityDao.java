package com.squadio.dao;

import com.squadio.entities.AccountEntity;
import com.squadio.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface AccountEntityDao extends CrudDao<AccountEntity,Long> {
    List<AccountEntity> findUserAccounts(UserEntity userEntity);
    Optional<AccountEntity> findByAccountId(String accountId);
}
