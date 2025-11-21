package com.example.bankapplication.service;

import com.example.bankapplication.dto.MortgageCheckRequest;
import com.example.bankapplication.dto.MortgageCheckResponse;
import com.example.bankapplication.exception.MortgageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MortgageServiceImplTest {

    private MortgageServiceImpl mortgageService;

    @BeforeEach
    void setUp() {
        mortgageService = new MortgageServiceImpl();
        mortgageService.initRates(); //populate the interest rates
    }

    @Test
    void shouldReturnFeasibleMortgage() {
        MortgageCheckRequest mortgageCheckRequest = new MortgageCheckRequest(
                new BigDecimal("60000"),
                20,
                new BigDecimal("200000"),
                new BigDecimal("250000")
        );

        MortgageCheckResponse mortgageCheckResponse = mortgageService.checkMortgage(mortgageCheckRequest);

        assertTrue(mortgageCheckResponse.feasible());
        assertNotNull(mortgageCheckResponse.monthlyCost());
        assertTrue(mortgageCheckResponse.monthlyCost().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void shouldReturnNotFeasibleWhenLoanExceedsIncomeLimit() {
        MortgageCheckRequest mortgageCheckRequest = new MortgageCheckRequest(
                new BigDecimal("30000"),
                20,
                new BigDecimal("200000"),
                new BigDecimal("250000")
        );

        MortgageCheckResponse mortgageCheckResponse = mortgageService.checkMortgage(mortgageCheckRequest);

        assertFalse(mortgageCheckResponse.feasible());
    }

    @Test
    void shouldThrowMortgageExceptionWhenNoMatchingMaturityPeriod() {
        MortgageCheckRequest mortgageCheckRequest = new MortgageCheckRequest(
                new BigDecimal("60000"),
                40,
                new BigDecimal("200000"),
                new BigDecimal("250000")
        );

        assertThrows(MortgageException.class, () -> mortgageService.checkMortgage(mortgageCheckRequest));
    }

    @Test
    void shouldReturnNotFeasibleWhenLoanExceedsHomeValue() {
        MortgageCheckRequest mortgageCheckRequest = new MortgageCheckRequest(
                new BigDecimal("60000"),
                20,
                new BigDecimal("300000"),
                new BigDecimal("250000")
        );

        MortgageCheckResponse response = mortgageService.checkMortgage(mortgageCheckRequest);

        assertFalse(response.feasible());
        assertTrue(response.monthlyCost().compareTo(BigDecimal.ZERO) > 0);
    }
}
