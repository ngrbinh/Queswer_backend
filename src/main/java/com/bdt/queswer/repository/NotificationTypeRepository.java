package com.bdt.queswer.repository;

import com.bdt.queswer.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType,Long> {
    public Optional<NotificationType> findByName(String name);
}
