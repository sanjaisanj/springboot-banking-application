package com.backend.bankingapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDto {

    private Long accountId;
    private String accountHolderName;
    private double balance;

    private List<TransactionDto>transactions;

}
