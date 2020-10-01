package com.bdt.queswer.model;

import lombok.Data;
import lombok.ToString;

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

    private Date creationDate;

    private Date lastEditDate;

    private String body;

    @ManyToOne
    @JoinColumn(name = "parentId")
    @ToString.Exclude
    private Post parentPost;

    @OneToMany(mappedBy = "parentPost")
    @ToString.Exclude
    private List<Post> childPosts;

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
    @JoinColumn(name = "postType")
    @ToString.Exclude
    private PostType postType;

    @ManyToOne
    @JoinColumn(name = "gradeType")
    @ToString.Exclude
    private GradeType gradeType;

    @ManyToOne
    @JoinColumn(name = "subjectType")
    @ToString.Exclude
    private  SubjectType subjectType;
}
