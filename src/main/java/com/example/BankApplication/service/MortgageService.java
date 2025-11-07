package com.example.BankApplication.service;

import com.example.BankApplication.dto.InterestRate;
import com.example.BankApplication.dto.MortgageCheckRequest;
import com.example.BankApplication.dto.MortgageCheckResponse;

import java.util.List;

public interface MortgageService {
    List<InterestRate> getInterestRates();

    MortgageCheckResponse checkMortgage(MortgageCheckRequest request);
}
