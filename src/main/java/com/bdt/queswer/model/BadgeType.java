package com.bdt.queswer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "BadgeTypes")
public class BadgeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int pointToAchieve;

    private String description;

    private String color;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<Badge> badges;
}
