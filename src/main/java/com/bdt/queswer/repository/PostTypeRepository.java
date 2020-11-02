package com.bdt.queswer.repository;

import com.bdt.queswer.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType,Long> {
    public Optional<PostType> findByName(String name);
}
