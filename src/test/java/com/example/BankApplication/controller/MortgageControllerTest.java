package com.example.BankApplication.controller;

import com.example.BankApplication.dto.InterestRate;
import com.example.BankApplication.dto.MortgageCheckResponse;
import com.example.BankApplication.service.MortgageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MortgageController.class)
class MortgageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MortgageService mortgageService;

    @Test
    void shouldReturnInterestRates() throws Exception {
        List<InterestRate> interestRates = List.of(
                new InterestRate(10, new BigDecimal("0.003"), LocalDateTime.now())
        );

        when(mortgageService.getInterestRates()).thenReturn(interestRates);

        mockMvc.perform(get("/api/interest-rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maturityPeriod").value(BigDecimal.valueOf(10)));
    }

    @Test
    void shouldReturnNotFeasibleWhenNoMatchMaturityPeriod() throws Exception {
        MortgageCheckResponse response = new MortgageCheckResponse(false, new BigDecimal("1000"));

        when(mortgageService.checkMortgage(any())).thenReturn(response);

        String requestJson = """
                    {
                      "income": 60000,
                      "loanValue": 200000,
                      "homeValue": 250000,
                      "maturityPeriod": 40
                    }
                """;

        mockMvc.perform(post("/api/mortgage-check")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturnFeasibleWhenLoanMeetsAllCriterias() throws Exception {
        MortgageCheckResponse response = new MortgageCheckResponse(true, new BigDecimal("1000"));

        when(mortgageService.checkMortgage(any())).thenReturn(response);


        String requestJson = """
                    {
                      "income": 60000,
                      "loanValue": 200000,
                      "homeValue": 250000,
                      "maturityPeriod": 20
                    }
                """;

        mockMvc.perform(post("/api/mortgage-check")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyCost").value("1000"));

    }

}
