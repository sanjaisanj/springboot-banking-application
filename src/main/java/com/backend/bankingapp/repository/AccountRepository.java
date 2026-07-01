package com.backend.bankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bankingapp.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByAccountHolderNameContainingIgnoreCase(String name);

}
