package com.backend.bankingapp.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {

    private LocalDateTime timeStamp;
    private String message;
    private String details;
    private String errorCode;
}
