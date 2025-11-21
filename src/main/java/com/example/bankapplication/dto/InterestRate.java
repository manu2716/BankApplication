package com.example.bankapplication.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InterestRate(int maturityPeriod, BigDecimal interestRate, LocalDateTime lastUpdate) {
}
