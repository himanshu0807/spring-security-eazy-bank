package com.eazybank.springsecurityeazybank.controller;

import com.eazybank.springsecurityeazybank.model.AccountTransactions;
import com.eazybank.springsecurityeazybank.model.Customer;
import com.eazybank.springsecurityeazybank.repository.AccountTransactionsJPARepository;
import com.eazybank.springsecurityeazybank.repository.CustomerJPARepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    @Autowired
    private AccountTransactionsJPARepository accountTransactionsRepository;

    @Autowired
    private CustomerJPARepository customerRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers != null && !customers.isEmpty()) {
            return accountTransactionsRepository.
                    findByCustomerIdOrderByTransactionDateDesc(customers.get(0).getId());
        }
        return Collections.emptyList();
    }
}
