package com.backend.bankingapp.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //handle specific exception -AccountException
     @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorDetails> handleAccountException(AccountException exception,
                                                                WebRequest webRequest){

            ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now()
            , exception.getMessage(), webRequest.getDescription(false),
             "ACCOUNT NOT FOUND");
            logger.error("Account Exception : {}", exception.getMessage());
             return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }

    //handle general exception
   @ExceptionHandler(Exception.class)
public ResponseEntity<ErrorDetails> handleGenericException(Exception exception, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now()
        , exception.getMessage(), webRequest.getDescription(false)
        , "INTERNAL_SERVER_ERROR");
        logger.error("Unexpected Error", exception);
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage()));

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
}

}
