package com.backend.bankingapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDto {

    private Long fromAccount;

    private Long toAccount;

    @Positive(message = "Transfer amount must be greater than 0")
    private double transferredAmount;

    private double senderBalance;

    private double receiverBalance;

    private LocalDateTime transactionTime;

}
