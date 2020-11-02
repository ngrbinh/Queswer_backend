package com.bdt.queswer.repository;

import com.bdt.queswer.model.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectType,Long> {
}
