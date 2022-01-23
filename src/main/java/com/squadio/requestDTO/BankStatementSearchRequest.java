package com.squadio.requestDTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class BankStatementSearchRequest {
    private String accountId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal fromAmount;
    private BigDecimal toAmount;
}
