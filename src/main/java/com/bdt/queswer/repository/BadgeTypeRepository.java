package com.bdt.queswer.repository;

import com.bdt.queswer.model.BadgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeTypeRepository extends JpaRepository<BadgeType,Long> {
}
