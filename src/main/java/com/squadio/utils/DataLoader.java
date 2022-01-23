package com.squadio.utils;

import com.squadio.constants.AccountTypeConstant;
import com.squadio.constants.CurrencyTypeConstant;
import com.squadio.constants.RoleTypeConstant;
import com.squadio.constants.TransactionStatusConstant;
import com.squadio.dao.AccountEntityDao;
import com.squadio.dao.TransactionEntityDao;
import com.squadio.dao.UserEntityDao;
import com.squadio.entities.AccountEntity;
import com.squadio.entities.TransactionEntity;
import com.squadio.entities.UserEntity;
import com.squadio.services.PasswordEncodingService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
@Slf4j
public class DataLoader {

    private final AccountEntityDao accountEntityDao;
    private final TransactionEntityDao transactionEntityDao;
    private final UserEntityDao userEntityDao;
    private final PasswordEncodingService passwordEncodingService;

    public DataLoader(AccountEntityDao accountEntityDao, TransactionEntityDao transactionEntityDao, UserEntityDao userEntityDao, PasswordEncodingService passwordEncodingService) {
        this.accountEntityDao = accountEntityDao;
        this.transactionEntityDao = transactionEntityDao;
        this.userEntityDao = userEntityDao;
        this.passwordEncodingService = passwordEncodingService;
    }

   public void createRecords(){
        createAdmin("Admin", "admin");
        String[] normalUsers = {"Mohammed", "John", "Kumar"};
        for (String u : normalUsers){
            UserEntity user =  createUser(u, "user", u);
            createAccountTransaction(user);
        }
    }

    private UserEntity createUser(String username, String password, String name){
        password = passwordEncodingService.encodePassword(password);
        log.info("Creating normal account for {}", username);
        UserEntity user =  UserEntity.builder().username(username)
                .password(password)
                .role(RoleTypeConstant.USER)
                .name(name)
                .userId(RandomGeneratorUtils.generateUserId())
                .build();
        return userEntityDao.saveRecord(user);
    }

    private UserEntity createAdmin(String username, String password){
        password = passwordEncodingService.encodePassword(password);
        log.info("Creating admin account for {}", username);

        UserEntity admin =   UserEntity.builder().username(username)
                .password(password)
                .role(RoleTypeConstant.ADMIN)
                .name("Admin")
                .userId(RandomGeneratorUtils.generateUserId())
                .build();
        return userEntityDao.saveRecord(admin);
    }

    private void createAccountTransaction(UserEntity userEntity){
        for (int i=0; i < 2; i++){
            AccountEntity account = createAccount(userEntity);
            createTransaction(account);
        }
    }

    private AccountEntity createAccount(UserEntity user){

        AccountEntity accountEntity = AccountEntity.builder()
                .accountNumber(RandomGeneratorUtils.generateRandomNumber(10))
                .accountType(AccountTypeConstant.SAVINGS)
                .balance(new BigDecimal(RandomGeneratorUtils.generateRandomNumber(3)))
                .currency(CurrencyTypeConstant.USD)
                .iBan(RandomGeneratorUtils.generateRandomNumber(10))
                .owner(user)
                .accountId(RandomGeneratorUtils.generateAccountId())
                .build();
        return accountEntityDao.saveRecord(accountEntity);

    }

    private void createTransaction(AccountEntity accountEntity){
        for (int i=0; i < 3; i++){
            BigDecimal amount = new BigDecimal(RandomGeneratorUtils.generateRandomNumber(5));
            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .account(accountEntity)
                    .transactionStatus(TransactionStatusConstant.PAID)
                    .amount(amount)
                    .description("Test transaction")
                    .build();
            transactionEntityDao.saveRecord(transactionEntity);
            log.info("Creating transaction record {}" , transactionEntity.getTransactionStatus());


        }
    }
}
