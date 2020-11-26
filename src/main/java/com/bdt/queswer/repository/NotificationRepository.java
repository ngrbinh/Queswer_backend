package com.bdt.queswer.repository;

import com.bdt.queswer.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    public Page<Notification> findAllByUserId(long userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Notification n set n.checked=true where n.userId = ?1")
    public void checkAllNotification(long userId);
}
