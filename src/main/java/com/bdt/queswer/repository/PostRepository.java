package com.bdt.queswer.repository;

import com.bdt.queswer.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Modifying
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = ?1 and p.postType.id = 1")
    public int addView(long postId);

    public List<Post> findAllByPostTypeIdAndIdIn(long postTypeId, List<Long> ids);

    @Transactional
    @Modifying
    @Query("update Post p set p.answerCount = p.answerCount -1 where p.id = ?1 and p.postType.id = 1")
    public int reduceAnswerCount(long postId);

    @Query(value = "select p from Post p where p.postTypeId = 1 " +
            "and (:subject is null or p.mySubjectTypeId = :subject) " +
            "and (:grade is null or p.myGradeTypeId = :grade) " +
            "and lower(p.body) like  lower(concat('%',:body,'%'))")
    public Page<Post> searchQuestions(@Param("subject") Long subjectId,
                                      @Param("grade") Long gradeId,
                                      @Param("body") String body, Pageable pageable);
}
