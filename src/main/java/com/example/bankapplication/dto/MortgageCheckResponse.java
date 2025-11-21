package com.example.bankapplication.dto;

import java.math.BigDecimal;

public record MortgageCheckResponse(boolean feasible, BigDecimal monthlyCost) {
}
