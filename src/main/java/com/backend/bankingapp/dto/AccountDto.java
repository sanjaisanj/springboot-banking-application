package com.backend.bankingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    @NotBlank(message = "Account holder name cannot be empty")
    private String accountHolderName;
    @Positive(message = "Initial balance must be greater than 0")
    private double balance;

}
