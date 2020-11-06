package com.bdt.queswer.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.security.acl.Owner;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String imgUrl;

    private Date creationDate;

    private Date lastEditDate;

    private String body;
    
    private Integer answerCount;

    private Integer voteCount = 0;

    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    @ToString.Exclude
    private Post parentPost;

    @OneToMany(mappedBy = "parentPost",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Post> answers;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Vote> votes;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "postTypeId")
    @ToString.Exclude
    private PostType postType;

    @ManyToOne
    @JoinColumn(name = "gradeTypeId")
    @ToString.Exclude
    private GradeType gradeType;

    @ManyToOne
    @JoinColumn(name = "subjectTypeId")
    @ToString.Exclude
    private  SubjectType subjectType;
}
