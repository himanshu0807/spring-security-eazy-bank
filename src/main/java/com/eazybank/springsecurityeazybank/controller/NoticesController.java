package com.eazybank.springsecurityeazybank.controller;

import com.eazybank.springsecurityeazybank.model.Notices;
import com.eazybank.springsecurityeazybank.repository.NoticesJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class NoticesController {

    @Autowired
    private NoticesJPARepository noticeRepository;

    @GetMapping("/notices")
    public ResponseEntity<List<Notices>> getNotices() {
        List<Notices> notices = noticeRepository.findAllActiveNotices();
        if (notices != null) {
            return ResponseEntity.ok()
                                 // to allow the request after 60 seconds meanwhile use from cache
                                 .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                                 .body(notices);
        } else {
            return null;
        }
    }
}