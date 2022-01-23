package com.squadio.entities;

import com.squadio.constants.AccountTypeConstant;
import com.squadio.constants.CurrencyTypeConstant;
import com.squadio.utils.EncryptionUtil;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account", indexes = @Index(columnList = "accountId"))
public class AccountEntity extends AbstractBaseEntity<Long>{

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountTypeConstant accountType;

    private String accountNumber;

    private String iBan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyTypeConstant currency;


    @Column(nullable = false)
    private BigDecimal balance;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity owner;

    public String getAccountNumber() {
        return EncryptionUtil.encryptObject(accountNumber);
    }



}
