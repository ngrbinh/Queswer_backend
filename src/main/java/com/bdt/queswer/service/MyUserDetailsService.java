package com.bdt.queswer.service;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.Account;
import com.bdt.queswer.model.MyUserDetails;
import com.bdt.queswer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = new MyUserDetails(account);
        /*System.out.println("Authorities khi qua đóng gói trong Userdetails :");
        userDetails.getAuthorities().forEach(authority -> {
            System.out.println(authority);
        });*/
        //User user = new User(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
        /*System.out.println("Authorities khi qua trả về bới UserDetailsService");
        user.getAuthorities().forEach(authority -> {
            System.out.println(authority);
        });*/
        return new User(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
    }

    public Boolean changePassword(String oldPassword, String newPassword) throws CustomException{
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByEmail(userName);
        if (account == null) {
            throw new UsernameNotFoundException(userName);
        }
        String fromReq = oldPassword;
        String fromDb = account.getPassword();
        if (!passwordEncoder.matches(fromReq,fromDb)) {
            throw new CustomException("Mật khẩu cũ không đúng", HttpStatus.FORBIDDEN);
        } else {
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
            return true;
        }
    }
}
