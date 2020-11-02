package com.bdt.queswer.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date creationDate;

    private String text;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId")
    @ToString.Exclude
    private Post post;
}
