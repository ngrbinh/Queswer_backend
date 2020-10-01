package com.bdt.queswer.model;

import javafx.geometry.Pos;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "gradeTypes")
public class GradeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "gradeType")
    @ToString.Exclude
    private List<Post> posts;
}
