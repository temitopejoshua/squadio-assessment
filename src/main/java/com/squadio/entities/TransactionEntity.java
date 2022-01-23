package com.squadio.entities;

import com.squadio.constants.TransactionStatusConstant;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class TransactionEntity extends AbstractBaseEntity<Long>{
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AccountEntity account;

    @Column(nullable = false)
    private BigDecimal amount;

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal fee = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatusConstant transactionStatus = TransactionStatusConstant.PENDING;

    private String description;

}
