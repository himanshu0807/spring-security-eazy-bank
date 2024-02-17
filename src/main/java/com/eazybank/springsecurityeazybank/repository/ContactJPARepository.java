package com.eazybank.springsecurityeazybank.repository;

import com.eazybank.springsecurityeazybank.model.ContactMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactJPARepository extends CrudRepository<ContactMessage, Long> {

}
