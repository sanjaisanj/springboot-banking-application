package com.backend.bankingapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.backend.bankingapp.dto.AccountDto;
import com.backend.bankingapp.dto.StatementDto;
import com.backend.bankingapp.dto.TransferResponseDto;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    List<AccountDto> searchAccounts(String name);

    AccountDto deposit(Long id,double amount);

    AccountDto withdraw(Long id,double amount);

    List<AccountDto>getAllAccounts();

    void deleteAccount(Long id);

    StatementDto getAccountStatement(Long accountId);

    TransferResponseDto accountTransfer(Long fromId,Long toId,double amount);

    Page<AccountDto> getAllAccounts(Pageable pageable);

    List<AccountDto> getAllAccounts(Sort sort);

   

}
