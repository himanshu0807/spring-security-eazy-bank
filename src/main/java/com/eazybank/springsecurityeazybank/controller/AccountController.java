package com.eazybank.springsecurityeazybank.controller;

import com.eazybank.springsecurityeazybank.model.Accounts;
import com.eazybank.springsecurityeazybank.model.Customer;
import com.eazybank.springsecurityeazybank.repository.AccountsJPARepository;
import com.eazybank.springsecurityeazybank.repository.CustomerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsJPARepository accountsRepository;

    @Autowired
    private CustomerJPARepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers != null && !customers.isEmpty()) {
            return accountsRepository.findByCustomerId(customers.get(0).getId());
        }
        return null;
    }
}