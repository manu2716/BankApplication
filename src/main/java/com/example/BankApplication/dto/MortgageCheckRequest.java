package com.example.BankApplication.dto;

import java.math.BigDecimal;

public record MortgageCheckRequest(BigDecimal income, int maturityPeriod,
                                   BigDecimal loanValue, BigDecimal homeValue) {
}
