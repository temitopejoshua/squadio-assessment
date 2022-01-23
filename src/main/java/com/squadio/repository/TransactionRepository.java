package com.squadio.repository;

import com.squadio.entities.AccountEntity;
import com.squadio.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long>, JpaSpecificationExecutor<TransactionEntity> {
    List<TransactionEntity> findAllByAccount(AccountEntity account);
}
