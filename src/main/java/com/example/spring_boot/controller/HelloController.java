package com.example.spring_boot.controller;

import com.example.spring_boot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @Autowired
    AccountService myAccountService;

    @GetMapping("/")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model) {
        model.addAttribute("name", name);
        boolean securityRisk = myAccountService.isSecurityRisk(name);
        model.addAttribute("securityRisk", securityRisk);
        return "greeting";
    }

}
