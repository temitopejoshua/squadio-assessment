package com.squadio.daoImpl;

import com.squadio.dao.TransactionEntityDao;
import com.squadio.entities.AccountEntity;
import com.squadio.entities.TransactionEntity;
import com.squadio.repository.TransactionRepository;
import com.squadio.requestDTO.StatementSearchRequestDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionEntityDaoImpl extends CrudDaoImpl<TransactionEntity,Long> implements TransactionEntityDao {
    private TransactionRepository repository;
    protected TransactionEntityDaoImpl(TransactionRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<TransactionEntity> findAllByAccount(StatementSearchRequestDTO searchRequestDTO) {
        Sort sort = Sort.by("dateCreated").descending();
        Specification<TransactionEntity> specification = Specification.where(withAccount(searchRequestDTO.getAccount()));
        specification = specification.and(withDateRange(searchRequestDTO.getFromDate(), searchRequestDTO.getToDate()));

        if(searchRequestDTO.getFromAmount() !=null && searchRequestDTO.getToAmount()!=null){
            specification = specification.and(withAmountRange(searchRequestDTO.getFromAmount(), searchRequestDTO.getToAmount()));
        }

        return repository.findAll(specification,sort);
    }

    private static Specification<TransactionEntity> withDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("dateCreated"), startTime, endTime));
    }
    private static Specification<TransactionEntity> withAmountRange(BigDecimal fromAmount, BigDecimal toAmount) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("amount"), fromAmount, toAmount));
    }

    private static Specification<TransactionEntity> withAccount(AccountEntity account) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("account"), account));
    }
}
