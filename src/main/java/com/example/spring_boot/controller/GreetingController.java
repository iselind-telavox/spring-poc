package com.example.spring_boot.controller;

import com.example.spring_boot.service.AccountService;
import com.example.spring_boot.service.RiskAssessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
public class GreetingController {
    public static final String THYMELEAF_GREETING_TEMPLATE = "greeting";
    private final AccountService myAccountService;
    private final RiskAssessor riskAssessor;

    public GreetingController(AccountService myAccountService, RiskAssessor riskAssessor) {
        this.myAccountService = myAccountService;
        this.riskAssessor = riskAssessor;
    }

    @GetMapping("/greeting")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model) {
        Optional<com.example.spring_boot.service.User> uu = myAccountService.getUser(name);

        if (uu.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown name");
        }

        com.example.spring_boot.service.User user = uu.get();
        boolean securityRisk = riskAssessor.isSecurityRisk(user);
        model.addAttribute("name", user.toString());

        model.addAttribute("securityRisk", securityRisk);
        return THYMELEAF_GREETING_TEMPLATE;
    }
}
