package com.example.BankApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnInterestRates() throws Exception {
        mockMvc.perform(get("/api/interest-rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].maturityPeriod").isNumber())
                .andExpect(jsonPath("$[0].interestRate").isNumber())
                .andExpect(jsonPath("$[0].lastUpdate").exists());
    }


    @Test
    void shouldReturnFeasibleMortgageCheck() throws Exception {
        String requestJson = """
                    {
                      "income": 60000,
                      "loanValue": 200000,
                      "homeValue": 250000,
                      "maturityPeriod": 20
                    }
                """;

        mockMvc.perform(post("/api/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyCost").isNumber());
    }

    @Test
    void shouldReturnNotFeasibleWhenLoanTooHigh() throws Exception {
        String requestJson = """
                    {
                      "income": 30000,
                      "loanValue": 200000,
                      "homeValue": 250000,
                      "maturityPeriod": 20
                    }
                """;

        mockMvc.perform(post("/api/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(jsonPath("$.feasible").value(false));
    }
}
