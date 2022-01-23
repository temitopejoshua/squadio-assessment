package com.squadio.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountStatementResponseDTO {
    private String accountNumber;
    private String description;
    private double amount;
    private String date;

}
