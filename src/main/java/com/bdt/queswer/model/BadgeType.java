package com.bdt.queswer.model;

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

    private String imageUrl;

    private String description;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Badge> badges;
}
