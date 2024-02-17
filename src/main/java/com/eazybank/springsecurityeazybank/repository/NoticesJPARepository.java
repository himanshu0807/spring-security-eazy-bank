package com.eazybank.springsecurityeazybank.repository;

import com.eazybank.springsecurityeazybank.model.Notices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticesJPARepository extends CrudRepository<Notices, Long> {

    @Query(value = "from Notices n where CURRENT_DATE BETWEEN noticeBeginDate AND noticeEndDate")
    List<Notices> findAllActiveNotices();

}
