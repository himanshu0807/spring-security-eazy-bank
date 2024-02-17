package com.eazybank.springsecurityeazybank.repository;

import com.eazybank.springsecurityeazybank.model.AccountTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionsJPARepository extends CrudRepository<AccountTransactions, Long> {

    List<AccountTransactions> findByCustomerIdOrderByTransactionDateDesc(int customerId);

}
