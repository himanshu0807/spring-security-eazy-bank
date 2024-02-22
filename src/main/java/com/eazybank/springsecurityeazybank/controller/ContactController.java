package com.eazybank.springsecurityeazybank.controller;

import com.eazybank.springsecurityeazybank.model.ContactMessage;
import com.eazybank.springsecurityeazybank.repository.ContactJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
public class ContactController {

    @Autowired
    private ContactJPARepository contactRepository;

    @PostMapping("/contact")
    // @PreFilter("filterObject.contactName != 'Test'") // to filter the data - applicable only for input type collection
    // @PostFilter("filterObject.contactName != 'Test'") // to filter the response and not to be send if the it is 'Test' - return type collection
    public List<ContactMessage> saveContactInquiryDetails(@RequestBody List<ContactMessage> contacts) {
        if (!contacts.isEmpty()) {
            ContactMessage contact = contacts.get(0);
            contact.setContactId(getServiceReqNumber());
            contact.setCreateDate(new Date(System.currentTimeMillis()));
            ContactMessage save = contactRepository.save(contact);
            List<ContactMessage> listContact = new ArrayList<>();
            listContact.add(save);
            return listContact;
        }
        return Collections.emptyList();
    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + ranNum;
    }
}