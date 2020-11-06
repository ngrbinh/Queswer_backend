package com.bdt.queswer.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "subjectTypes")
public class SubjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "subjectType", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Post> posts;
}
