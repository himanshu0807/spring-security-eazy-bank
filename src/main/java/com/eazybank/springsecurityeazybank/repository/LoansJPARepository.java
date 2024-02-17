package com.eazybank.springsecurityeazybank.repository;

import com.eazybank.springsecurityeazybank.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoansJPARepository extends CrudRepository<Loans, Long> {

    List<Loans> findByCustomerIdOrderByStartDateDesc(int customerId);

}
