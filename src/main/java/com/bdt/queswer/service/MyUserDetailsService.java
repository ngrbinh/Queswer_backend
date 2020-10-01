package com.bdt.queswer.service;

import com.bdt.queswer.model.Account;
import com.bdt.queswer.model.MyUserDetails;
import com.bdt.queswer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = new MyUserDetails(account);
        return new User(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
    }
}
