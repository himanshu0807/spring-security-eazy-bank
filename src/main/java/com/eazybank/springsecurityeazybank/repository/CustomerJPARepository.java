package com.eazybank.springsecurityeazybank.repository;

import com.eazybank.springsecurityeazybank.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerJPARepository extends CrudRepository<Customer, Integer> {

    List<Customer> findByEmail(String email);

}
