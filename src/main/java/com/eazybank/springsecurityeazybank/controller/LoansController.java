package com.eazybank.springsecurityeazybank.controller;

import com.eazybank.springsecurityeazybank.model.Customer;
import com.eazybank.springsecurityeazybank.model.Loans;
import com.eazybank.springsecurityeazybank.repository.CustomerJPARepository;
import com.eazybank.springsecurityeazybank.repository.LoansJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoansJPARepository loanRepository;

    @Autowired
    private CustomerJPARepository customerRepository;

    @GetMapping("/myLoans")
    //    @PostAuthorize("hasRole('ROOT')")// method will not be able to return response as the user has no authZ
    public List<Loans> getLoanDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers != null && !customers.isEmpty()) {
            return loanRepository.
                    findByCustomerIdOrderByStartDateDesc(customers.get(0).getId());
        }
        return Collections.emptyList();
    }
}