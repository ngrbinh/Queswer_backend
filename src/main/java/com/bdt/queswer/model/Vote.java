package com.bdt.queswer.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    @ToString.Exclude
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "userId")
    private User user;

    @Column(name="postId", insertable = false, updatable = false)
    private long postId;

    @Column(name="userId", insertable = false, updatable = false)
    private long userId;

}
