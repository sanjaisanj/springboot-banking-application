package com.backend.bankingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequestDto {

    private Long toAccount;
    private double amount;

}
