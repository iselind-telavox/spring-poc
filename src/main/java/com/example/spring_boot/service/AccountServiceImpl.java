package com.example.spring_boot.service;

import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final RiskAssessor riskAssessor;

    public AccountServiceImpl(RiskAssessor riskAssessor) {
        this.riskAssessor = riskAssessor;
    }

    public boolean isSecurityRisk(String name) {
        return riskAssessor.assess(name);
    }

}
