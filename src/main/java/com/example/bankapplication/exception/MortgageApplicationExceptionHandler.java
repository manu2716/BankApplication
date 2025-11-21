package com.example.bankapplication.exception;

import com.example.bankapplication.dto.MortgageCheckResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.math.BigDecimal;

@RestControllerAdvice
public class MortgageApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MortgageException.class)
    public MortgageCheckResponse handleMortgageException(Exception e) {
        return new MortgageCheckResponse(false, BigDecimal.ZERO);
    }

    @ExceptionHandler(Exception.class)
    public MortgageGenericErrorResponse handleHttpMessageNotReadableException(Exception ex) {
        return new MortgageGenericErrorResponse("Unexpected error: " + ex.getMessage());
    }
}
