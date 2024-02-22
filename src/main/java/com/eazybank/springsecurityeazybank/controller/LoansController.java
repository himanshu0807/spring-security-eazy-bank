package com.eazybank.springsecurityeazybank.controller;

import com.eazybank.springsecurityeazybank.model.Loans;
import com.eazybank.springsecurityeazybank.repository.LoansJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoansJPARepository loanRepository;

    @GetMapping("/myLoans")
//    @PostAuthorize("hasRole('ROOT')")// method will not be able to return response as the user has no authZ
    public List<Loans> getLoanDetails(@RequestParam int id) {
        return loanRepository.findByCustomerIdOrderByStartDateDesc(id);
    }
}