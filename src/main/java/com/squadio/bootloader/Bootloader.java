package com.squadio.bootloader;

import com.squadio.dao.AccountEntityDao;
import com.squadio.dao.TransactionEntityDao;
import com.squadio.dao.UserEntityDao;
import com.squadio.services.PasswordEncodingService;
import com.squadio.utils.DataLoader;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true)
@Component
@AllArgsConstructor
@Slf4j
public class Bootloader implements CommandLineRunner {
    private final AccountEntityDao accountEntityDao;
    private final TransactionEntityDao transactionEntityDao;
    private final UserEntityDao userEntityDao;
    private final PasswordEncodingService passwordEncodingService;

    @Override
    public void run(String... args) throws Exception {
        DataLoader dataloader = new DataLoader(accountEntityDao,transactionEntityDao,userEntityDao,passwordEncodingService);
        dataloader.createRecords();

    }



}
