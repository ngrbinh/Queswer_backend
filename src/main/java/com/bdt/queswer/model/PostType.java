package com.bdt.queswer.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "postTypes")
public class PostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "postType",fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Post> posts;
}
