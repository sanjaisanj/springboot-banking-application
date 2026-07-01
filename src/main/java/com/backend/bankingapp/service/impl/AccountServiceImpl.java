package com.backend.bankingapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.bankingapp.dto.AccountDto;
import com.backend.bankingapp.dto.StatementDto;
import com.backend.bankingapp.dto.TransferResponseDto;
import com.backend.bankingapp.entity.Account;
import com.backend.bankingapp.exception.AccountException;
import com.backend.bankingapp.mapper.AccountMapper;
import com.backend.bankingapp.repository.AccountRepository;
import com.backend.bankingapp.service.AccountService;
import com.backend.bankingapp.service.TransactionService;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService{

     private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
     private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        
       Account account = AccountMapper.mapToAccount(accountDto);
       logger.info("Creating account for {}", accountDto.getAccountHolderName());
       Account savedAccount=accountRepository.save(account);
       logger.info("Account created successfully with ID {}", savedAccount.getId());

       return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                         .findById(id)
                         .orElseThrow(()-> new AccountException("Account does not exist."));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=accountRepository
                         .findById(id)
                         .orElseThrow(()-> new AccountException("Account does not exist."));
        double total = account.getBalance()+amount;
        logger.info("Deposit request received. Account={}, Amount={}", id, amount);
        account.setBalance(total);
        Account savedAccount= accountRepository.save(account);
        logger.info("Deposit successful. Updated Balance={}", account.getBalance());
       transactionService.saveTransaction(id,"DEPOSIT",amount);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository
                         .findById(id)
                         .orElseThrow(()-> new AccountException("Account does not exist."));

       if(account.getBalance()<amount){
        throw new AccountException("Insufficient Balance.");
       }

       double total=account.getBalance()-amount;
       logger.info("Withdraw request. Account={}, Amount={}", id, amount);
       account.setBalance(total);
       Account savedAccount=accountRepository.save(account);
       logger.info("Withdraw successful. Remaining Balance={}", account.getBalance());
       transactionService.saveTransaction(id,"WITHDRAW",amount);
       return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account>accounts=accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
        
    }

    @Override
    public void deleteAccount(Long id) {
        logger.info("Deleting account {}", id);
        Account account=accountRepository
                         .findById(id)
                         .orElseThrow(()-> new AccountException("Account does not exist."));

                         logger.info("Deleting account {}", id);
        accountRepository.deleteById(id);
        logger.info("Account {} deleted successfully", id);
    }
    
  @Transactional
  @Override
    public TransferResponseDto accountTransfer(Long fromId, Long toId,double amount) {

    if(amount <= 0){
        throw new AccountException("Amount should be greater than zero.");
    }

    if(fromId.equals(toId)){
        throw new AccountException("Cannot transfer to same account.");
    }

    Account fromAccount =
            accountRepository.findById(fromId)
            .orElseThrow(() ->
            new AccountException("Sender account not found"));

    Account toAccount =
            accountRepository.findById(toId)
            .orElseThrow(() ->
            new AccountException("Receiver account not found"));

    if(fromAccount.getBalance() < amount){
        throw new AccountException("Insufficient Balance.");
    }

    logger.info("Transfer Started. From={}, To={}, Amount={}",fromId,toId,amount);
    fromAccount.setBalance(fromAccount.getBalance() - amount);

    toAccount.setBalance(toAccount.getBalance() + amount);

    accountRepository.save(fromAccount);

    accountRepository.save(toAccount);
    transactionService.saveTransaction(fromId,"TRANSFER SENT",amount);

    transactionService.saveTransaction(toId,"TRANSFER RECEIVED",amount);
    logger.info("Transfer Completed. From={}, To={}, Amount={}",fromId,toId,amount);

    TransferResponseDto dto = new TransferResponseDto();

    dto.setFromAccount(fromId);
    dto.setToAccount(toId);
    dto.setTransferredAmount(amount);
    dto.setSenderBalance(fromAccount.getBalance());
    dto.setReceiverBalance(toAccount.getBalance());
    dto.setTransactionTime(LocalDateTime.now());

    return dto;
    }

  @Override
public StatementDto getAccountStatement(Long accountId) {

    Account account = accountRepository.findById(accountId)
            .orElseThrow(() ->
                    new AccountException("Account does not exist."));

    StatementDto statement = new StatementDto();

    statement.setAccountId(account.getId());
    statement.setAccountHolderName(account.getAccountHolderName());
    statement.setBalance(account.getBalance());

    statement.setTransactions(
            transactionService.getTransactionsByAccountId(accountId)
    );

    return statement;
}

   @Override
public List<AccountDto> searchAccounts(String name) {

    List<Account> accounts = accountRepository.findByAccountHolderNameContainingIgnoreCase(name);

    return accounts.stream()
            .map(AccountMapper::mapToAccountDto)
            .toList();
}

    @Override
     public Page<AccountDto> getAllAccounts(Pageable pageable) {

    Page<Account> accounts = accountRepository.findAll(pageable);

    return accounts.map(AccountMapper::mapToAccountDto);
   }

   @Override
    public List<AccountDto> getAllAccounts(Sort sort) {

    List<Account> accounts = accountRepository.findAll(sort);

    return accounts.stream()
            .map(AccountMapper::mapToAccountDto)
            .toList();
    }
}
