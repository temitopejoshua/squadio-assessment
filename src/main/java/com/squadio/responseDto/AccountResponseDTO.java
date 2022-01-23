package com.squadio.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponseDTO {
    private String accountId;
    private String accountType;
    private String accountNumber;
    private String  IBAN;
    private double balance;
    private String currency;
}

