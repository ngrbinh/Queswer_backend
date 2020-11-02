package com.bdt.queswer.repository;

import com.bdt.queswer.model.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<GradeType,Long> {
}
