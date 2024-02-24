package com.eazybank.springsecurityeazybank.controller;

import com.eazybank.springsecurityeazybank.model.Cards;
import com.eazybank.springsecurityeazybank.model.Customer;
import com.eazybank.springsecurityeazybank.repository.CardsJPARepository;
import com.eazybank.springsecurityeazybank.repository.CustomerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsJPARepository cardsRepository;

    @Autowired
    private CustomerJPARepository customerRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers != null && !customers.isEmpty()) {
            return cardsRepository.findByCustomerId(customers.get(0).getId());
        }
        return Collections.emptyList();
    }
}
