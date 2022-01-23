package com.squadio.daoImpl;

import com.squadio.dao.AccountEntityDao;
import com.squadio.entities.AccountEntity;
import com.squadio.entities.UserEntity;
import com.squadio.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountEntityDaoImpl extends CrudDaoImpl<AccountEntity,Long> implements AccountEntityDao {

    private AccountRepository repository;

    public AccountEntityDaoImpl(AccountRepository repository) {
        super(repository);
        this.repository=repository;
    }

    @Override
    public List<AccountEntity> findUserAccounts(UserEntity userEntity) {
        return repository.findAllByOwner(userEntity);
    }

    @Override
    public Optional<AccountEntity> findByAccountId(String accountId) {
        return repository.findByAccountId(accountId);
    }
}
