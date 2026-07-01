package com.backend.bankingapp.controller;

import java.net.ResponseCache;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bankingapp.dto.AccountDto;
import com.backend.bankingapp.dto.StatementDto;
import com.backend.bankingapp.dto.TransferRequestDto;
import com.backend.bankingapp.dto.TransferResponseDto;
import com.backend.bankingapp.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@Tag(
    name = "Account Management",
    description = "REST APIs for Banking Application"
)
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }

    //add  account REST API
    @Operation(summary = "Create a New Bank Account")
    @PostMapping
    public ResponseEntity<AccountDto> addAccount( @Valid @RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto),HttpStatus.CREATED);
    }

    //get account REST API
    @Operation(summary = "Get Account By Id")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }
    
    // Deposit REST API
    @Operation(summary = "Deposit Money")
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,@RequestBody Map<String,Double>request){

        AccountDto accountDto=accountService.deposit(id, request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    //Withdraw REST API
    @Operation(summary = "Withdraw Money")
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,@RequestBody Map<String,Double>request){
       
        AccountDto accountDto = accountService.withdraw(id, request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    // Get All Accounts
    @Operation(summary = "Get All Accounts")
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto>accounts=accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Delete account REST API
    @Operation(summary = "Delete Account")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted successfully!");
    }

    //Account transfer REST API
    @Operation(summary = "Transfer Money")
@PutMapping("/{id}/accountTransfer")
public ResponseEntity<TransferResponseDto> accountTransfer(
        @PathVariable Long id,
        @RequestBody TransferRequestDto request){

    TransferResponseDto response =
            accountService.accountTransfer(
                    id,
                    request.getToAccount(),
                    request.getAmount());

    return ResponseEntity.ok(response);
}
   
   // statement REST API
   @Operation(summary = "Statement")
   @GetMapping("/{id}/statement")
   public ResponseEntity<StatementDto> getAccountStatement(@PathVariable("id") Long accountId) {

    StatementDto statement = accountService.getAccountStatement(accountId);

    return ResponseEntity.ok(statement);
}
    
@Operation(summary = "Search by Name")
    @GetMapping("/search")
    public ResponseEntity<List<AccountDto>> searchAccounts(@RequestParam String name) {

    List<AccountDto> accounts = accountService.searchAccounts(name);

    return ResponseEntity.ok(accounts);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AccountDto>> getAccountsByPage(Pageable pageable) {

    return ResponseEntity.ok(accountService.getAllAccounts(pageable));
}
  
    @Operation(summary = "Sorting")
    @GetMapping("/sort")
public ResponseEntity<List<AccountDto>> getSortedAccounts(
        @RequestParam String field,
        @RequestParam(defaultValue = "asc") String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(field).descending()
            : Sort.by(field).ascending();

    return ResponseEntity.ok(accountService.getAllAccounts(sort));
}

}
