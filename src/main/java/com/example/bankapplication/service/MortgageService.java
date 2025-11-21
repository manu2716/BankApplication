package com.example.bankapplication.service;

import com.example.bankapplication.dto.InterestRate;
import com.example.bankapplication.dto.MortgageCheckRequest;
import com.example.bankapplication.dto.MortgageCheckResponse;

import java.util.List;

public interface MortgageService {
    List<InterestRate> getInterestRates();

    MortgageCheckResponse checkMortgage(MortgageCheckRequest request);
}
