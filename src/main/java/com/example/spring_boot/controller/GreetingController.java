package com.example.spring_boot.controller;

import com.example.spring_boot.service.AccountService;
import com.example.spring_boot.service.RiskAssessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class GreetingController {
    @Autowired
    AccountService myAccountService;

    @Autowired
    RiskAssessor riskAssessor;

    @GetMapping("/greeting")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model) {
        Optional<com.example.spring_boot.service.User> uu = myAccountService.getUser(name);
        boolean securityRisk;

        if (uu.isPresent()) {
            com.example.spring_boot.service.User user = uu.get();
            securityRisk = riskAssessor.isSecurityRisk(user);
            model.addAttribute("name", user.toString());
        } else {
            model.addAttribute("name", name);
            securityRisk = riskAssessor.isSecurityRisk(name);
        }

        model.addAttribute("securityRisk", securityRisk);
        return "greeting";
    }

}
