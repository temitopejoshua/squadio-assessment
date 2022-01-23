package com.squadio.repository;

import com.squadio.entities.AccountEntity;
import com.squadio.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
    List<AccountEntity> findAllByOwner(UserEntity userEntity);
    Optional<AccountEntity> findByAccountId(String accountId);
}
