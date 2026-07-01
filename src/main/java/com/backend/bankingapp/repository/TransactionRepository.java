package com.backend.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.bankingapp.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

}
