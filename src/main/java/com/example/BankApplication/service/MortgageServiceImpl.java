package com.example.BankApplication.service;

import com.example.BankApplication.dto.InterestRate;
import com.example.BankApplication.dto.MortgageCheckRequest;
import com.example.BankApplication.dto.MortgageCheckResponse;
import com.example.BankApplication.exception.MortgageException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MortgageServiceImpl implements MortgageService {

    private static final Logger log = LoggerFactory.getLogger(MortgageServiceImpl.class);
    private final List<InterestRate> interestRateList = new ArrayList<>();

    @PostConstruct
    public void initRates() {
        interestRateList.add(new InterestRate(10, new BigDecimal("0.003"),
                LocalDateTime.now()));
        interestRateList.add(new InterestRate(20, new BigDecimal("0.004"),
                LocalDateTime.now()));
        interestRateList.add(new InterestRate(30, new BigDecimal("0.005"),
                LocalDateTime.now()));
    }

    public List<InterestRate> getInterestRates() {
        return interestRateList;
    }

    public MortgageCheckResponse checkMortgage(MortgageCheckRequest request) {

        var interestRate = interestRateList.stream()
                .filter(r -> r.maturityPeriod() == request.maturityPeriod())
                .findFirst()
                .orElseThrow(() -> new MortgageException("No Interest Rate Found for the given Maturity Period"));


        var monthlyCost = getMonthlyCost(request, interestRate);

        var income4Times = request.income().multiply(BigDecimal.valueOf(4));
        var loanValue = request.loanValue();
        boolean feasible = loanValue.compareTo(income4Times) <= 0
                && loanValue.compareTo(request.homeValue()) <= 0;

        return new MortgageCheckResponse(feasible, monthlyCost);
    }


    private BigDecimal getMonthlyCost(MortgageCheckRequest request, InterestRate interestRate) {
        var rate = interestRate.interestRate();
        var monthlyRate = rate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        var months = request.maturityPeriod() * 12;
        var monthlyCost =
                request.loanValue()
                        .multiply(monthlyRate)
                        .divide(BigDecimal.ONE.subtract(
                                        BigDecimal.ONE.add(monthlyRate).pow(-months, new MathContext(10))),
                                2, RoundingMode.HALF_UP);
        return monthlyCost;
    }
}
