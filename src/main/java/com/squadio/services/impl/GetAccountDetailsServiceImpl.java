package com.squadio.services.impl;

import com.squadio.dao.AccountEntityDao;
import com.squadio.dao.TransactionEntityDao;
import com.squadio.dao.UserEntityDao;
import com.squadio.entities.AccountEntity;
import com.squadio.entities.TransactionEntity;
import com.squadio.entities.UserEntity;
import com.squadio.exception.BadRequestException;
import com.squadio.exception.NotFoundException;
import com.squadio.exception.UnAuthorizedException;
import com.squadio.requestDTO.BankStatementSearchRequest;
import com.squadio.requestDTO.StatementSearchRequestDTO;
import com.squadio.responseDto.AccountResponseDTO;
import com.squadio.responseDto.AccountStatementResponseDTO;
import com.squadio.responseDto.UserResponseDTO;
import com.squadio.security.AuthenticatedUser;
import com.squadio.services.GetAccountDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAccountDetailsServiceImpl implements GetAccountDetailsService {
    private final AccountEntityDao accountEntityDao;
    private final TransactionEntityDao transactionEntityDao;
    private final UserEntityDao userEntityDao;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userEntityDao.findAll().stream().map(this::mapUserEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserByUsername(AuthenticatedUser currentUser, String username) {
        UserEntity userEntity = userEntityDao.findByUsername(username).orElseThrow(() -> new NotFoundException("No username found " + username));
        if(!userEntity.getUserId().equalsIgnoreCase(currentUser.getUserId()) && !currentUser.isAdmin()){
            throw new UnAuthorizedException("Unauthorized");
        }
        return this.mapUserEntityToDTO(userEntity);
    }

    @Override
    public List<AccountResponseDTO> getAllAccountByUser(AuthenticatedUser currentUser, String  userId) {
        UserEntity userEntity = userEntityDao.getByUserId(userId);
        if(!userEntity.getUserId().equalsIgnoreCase(currentUser.getUserId()) && !currentUser.isAdmin()){
            throw new UnAuthorizedException("Unauthorized");
        }
        List<AccountEntity> accounts = accountEntityDao.findUserAccounts(userEntity);

        return accounts.stream().map(this::mapToAccountResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<AccountStatementResponseDTO> getAccountStatement(AuthenticatedUser currentUser, BankStatementSearchRequest searchRequest) {
        UserEntity userEntity = userEntityDao.getByUserId(currentUser.getUserId());
        if(!userEntity.getUserId().equalsIgnoreCase(currentUser.getUserId()) && !currentUser.isAdmin()){
            throw new UnAuthorizedException("Unauthorized");
        }
        AccountEntity accountEntity = accountEntityDao.findByAccountId(searchRequest.getAccountId()).orElseThrow(() -> new BadRequestException("Invalid account id"));
        StatementSearchRequestDTO searchDTO = StatementSearchRequestDTO.builder().account(accountEntity).build();

        if (searchRequest.getFromDate() != null) {
            searchDTO.setFromDate(searchRequest.getFromDate().atStartOfDay());
        }else {
            LocalDateTime _3MonthsAgo = LocalDateTime.now().minusMonths(3);
            searchDTO.setFromDate(_3MonthsAgo);
        }
        if (searchRequest.getToDate() != null) {
            searchDTO.setToDate(searchRequest.getToDate().atTime(LocalTime.MAX));
        }else {
            searchDTO.setToDate(LocalDateTime.now());
        }

        List<TransactionEntity> transactions = transactionEntityDao.findAllByAccount(searchDTO);
        return transactions.stream().map((trn) -> this.mapToAccountResponse(trn,accountEntity)).collect(Collectors.toList());
    }

    private UserResponseDTO mapUserEntityToDTO(UserEntity userEntity){
        return UserResponseDTO.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .role(userEntity.getRole().name())
                .username(userEntity.getUsername())
                .build();
    }
    private AccountResponseDTO mapToAccountResponseDTO(AccountEntity accountEntity){
       return AccountResponseDTO.builder()
               .accountId(accountEntity.getAccountId())
                .accountNumber(accountEntity.getAccountNumber())
                .accountType(accountEntity.getAccountType().name())
                .balance(accountEntity.getBalance().doubleValue())
                .currency(accountEntity.getCurrency().name())
                .IBAN(accountEntity.getIBan())
                .build();
    }

    private AccountStatementResponseDTO mapToAccountResponse(TransactionEntity transactionEntity, AccountEntity account){
        return AccountStatementResponseDTO.builder()
                .accountNumber(account.getAccountNumber())
                .amount(transactionEntity.getAmount().doubleValue())
                .date(transactionEntity.getDateCreated().format(DateTimeFormatter.ISO_DATE))
                .description(transactionEntity.getDescription())
                .build();
    }
}
