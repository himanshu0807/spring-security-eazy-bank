package com.eazybank.springsecurityeazybank.controller;

import com.eazybank.springsecurityeazybank.model.AccountTransactions;
import com.eazybank.springsecurityeazybank.repository.AccountTransactionsJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    @Autowired
    private AccountTransactionsJPARepository accountTransactionsRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam int id) {
        return accountTransactionsRepository.
                findByCustomerIdOrderByTransactionDateDesc(id);
    }
}
