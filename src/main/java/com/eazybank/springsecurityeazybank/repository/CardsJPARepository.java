package com.eazybank.springsecurityeazybank.repository;

import com.eazybank.springsecurityeazybank.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsJPARepository extends CrudRepository<Cards, Long> {

    List<Cards> findByCustomerId(int customerId);

}
