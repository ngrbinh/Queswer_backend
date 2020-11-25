package com.bdt.queswer.repository;

import com.bdt.queswer.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findAllByPostTypeId(long id, Pageable pageable);

    public Optional<Post> findByIdAndPostTypeId(long id, long postTypeId);

    public Page<Post> findAllByPostTypeIdAndOwnerId(long postTypeId, long userId, Pageable pageable);

    public long countByPostTypeId(long id);

    @Query("SELECT sum(p.viewCount) from Post p where p.postType.id = 1")
    public int sumViewCount();
}
