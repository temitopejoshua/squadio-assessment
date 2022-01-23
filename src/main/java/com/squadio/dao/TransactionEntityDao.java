package com.squadio.dao;

import com.squadio.entities.TransactionEntity;
import com.squadio.requestDTO.StatementSearchRequestDTO;

import java.util.List;

public interface TransactionEntityDao extends CrudDao<TransactionEntity,Long>{
    List<TransactionEntity> findAllByAccount(StatementSearchRequestDTO searchRequestDTO);
}
