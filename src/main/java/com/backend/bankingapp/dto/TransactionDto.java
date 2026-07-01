package com.backend.bankingapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long id;
    private Long accountId;
    private String transactionType;
    private double amount;
    private LocalDateTime transactionTime;

}