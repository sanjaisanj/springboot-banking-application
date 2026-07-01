package com.backend.bankingapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.backend.bankingapp.exception.AccountException;

import org.springframework.stereotype.Service;

import com.backend.bankingapp.dto.TransactionDto;
import com.backend.bankingapp.entity.Transaction;
import com.backend.bankingapp.mapper.TransactionMapper;
import com.backend.bankingapp.repository.TransactionRepository;
import com.backend.bankingapp.service.TransactionService;


@Service
public class TransactionServiceImpl implements TransactionService{
   
    
    private final TransactionRepository repository;
    TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionDto saveTransaction(Long accountId,String type,double amount) {

        Transaction transaction = new Transaction();

        transaction.setAccountId(accountId);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setTransactionTime(LocalDateTime.now());

        Transaction saved =
                repository.save(transaction);

        return TransactionMapper.mapToDto(saved);
    }

    @Override
public List<TransactionDto> getTransactionsByAccountId(Long accountId) {

    List<Transaction> transactions =
            repository.findByAccountId(accountId);

    return transactions.stream()
            .map(TransactionMapper::mapToDto)
            .toList();
}

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction>transactions=repository.findAll();
        return transactions.stream().map((transaction) -> TransactionMapper.mapToDto(transaction))
                .collect(Collectors.toList());
    }
}