package com.example.BankApplication.exception;

import com.example.BankApplication.dto.MortgageCheckResponse;
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

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
//        log.error("Bad Request ", exception);
//        return createProblemDetail(exception, HttpStatus.BAD_REQUEST, "Invalid request");
//    }
}
