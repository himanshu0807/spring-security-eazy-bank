package com.eazybank.springsecurityeazybank.service;

import com.eazybank.springsecurityeazybank.model.Customer;
import com.eazybank.springsecurityeazybank.repository.CustomerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
As we are giving the implementation for the AuthenticationProvider we are we do not need the EBUserDetail service class that is used by DaoAuthenticationProvider
 */
//@Service
public class EazyBankUserDetails implements UserDetailsService {

    @Autowired
    CustomerJPARepository jpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName;
        String password;
        List<GrantedAuthority> authorities;
        List<Customer> customers = jpaRepository.findByEmail(username);
        if (customers.size() == 0) {
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        } else {
            userName = customers.get(0).getEmail();
            password = customers.get(0).getPwd();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
        }
        return new User(userName, password, authorities);
    }
}
