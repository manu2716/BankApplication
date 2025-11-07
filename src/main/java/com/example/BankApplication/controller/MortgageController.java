package com.example.BankApplication.controller;

import com.example.BankApplication.dto.InterestRate;
import com.example.BankApplication.dto.MortgageCheckRequest;
import com.example.BankApplication.dto.MortgageCheckResponse;
import com.example.BankApplication.service.MortgageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Mortgage API", description = "Endpoints for mortgage interest rates and feasibility check")
public class MortgageController {

    private static final Logger log = LoggerFactory.getLogger(MortgageController.class);
    private final MortgageService mortgageService;

    public MortgageController(MortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }

    @Operation(summary = "Get interest rates")
    @ApiResponse(responseCode = "200", description = "List of interest rates")
    @GetMapping("/interest-rates")
    public ResponseEntity<List<InterestRate>> getRates() {
        List<InterestRate> rates = mortgageService.getInterestRates();
        return ResponseEntity.ok().body(rates);
    }

    // can add validations to the request body
    @Operation(summary = "check mortgage feasibility")
    @ApiResponse(responseCode = "200", description = "mortgage feasibility and monthly cost")
    @PostMapping("/mortgage-check")
    public ResponseEntity<MortgageCheckResponse> checkMortgage(
            @RequestBody @Valid MortgageCheckRequest mortgageCheckRequest) {
        var response = mortgageService.checkMortgage(mortgageCheckRequest);

        // Builder pattern
        if (!response.feasible()) {
            log.error("Mortgage Feasibility check failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok().body(response);
    }
}
