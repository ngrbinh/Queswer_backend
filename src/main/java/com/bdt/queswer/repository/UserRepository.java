package com.bdt.queswer.repository;

import com.bdt.queswer.model.Account;
import com.bdt.queswer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends  JpaRepository<User,Long>{
    boolean existsByDisplayName(String displayName);

    Optional<User> findByAccountEmail(String email);
}
