package com.squadio.requestDTO;

import com.squadio.entities.AccountEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
public class StatementSearchRequestDTO {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private BigDecimal fromAmount;
    private BigDecimal toAmount;
    private AccountEntity account;

}