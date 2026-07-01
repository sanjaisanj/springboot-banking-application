package com.backend.bankingapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bankingapp.dto.TransactionDto;
import com.backend.bankingapp.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    //get transaction REST API
   @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccountId(@PathVariable Long accountId) {

    List<TransactionDto> transactions =transactionService.getTransactionsByAccountId(accountId);

    return ResponseEntity.ok(transactions);
}
   
    //get all transactions REST  API
    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllAccounts(){
        List<TransactionDto>transactions=transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
