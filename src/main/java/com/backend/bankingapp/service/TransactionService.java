package com.backend.bankingapp.service;


import java.util.List;

import com.backend.bankingapp.dto.TransactionDto;

public interface TransactionService {
     TransactionDto saveTransaction(Long accountId,String type,double amount);

     List<TransactionDto> getTransactionsByAccountId(Long accountId);
     List<TransactionDto>getAllTransactions();

}
