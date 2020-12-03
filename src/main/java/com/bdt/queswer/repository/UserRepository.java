package com.bdt.queswer.repository;

import com.bdt.queswer.model.Account;
import com.bdt.queswer.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByDisplayName(String displayName);

    Optional<User> findByAccountEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.point = u.point + ?2 where u.id = ?1")
    void updatePointByUserId(long userId, int point);

    @Transactional
    @Modifying
    @Query("update User u set u.answerCount = u.answerCount + ?2 where u.id = ?1")
    void modifyAnswerCount(long ownerId, int add);

    @Transactional
    @Modifying
    @Query("update User u set u.questionCount = u.questionCount + ?2 where u.id = ?1")
    void modifyQuestionCount(long ownerId, int add);

    @Query(value = "select u from User u where lower(u.displayName) like  lower(concat('%',:name,'%'))")
    Page<User> searchUserByName(@Param("name") String name, Pageable pageable);
}
