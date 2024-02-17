package com.eazybank.springsecurityeazybank.repository;

import com.eazybank.springsecurityeazybank.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountsJPARepository extends CrudRepository<Accounts, Long> {
	
	Accounts findByCustomerId(int customerId);

}
