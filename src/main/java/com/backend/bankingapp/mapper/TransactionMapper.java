package com.backend.bankingapp.mapper;

import com.backend.bankingapp.dto.TransactionDto;
import com.backend.bankingapp.entity.Transaction;

public class TransactionMapper {

    public static TransactionDto mapToDto(Transaction transaction){

        TransactionDto dto = new TransactionDto();

        dto.setId(transaction.getId());
        dto.setAccountId(transaction.getAccountId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionTime(transaction.getTransactionTime());

        return dto;
    }

}
