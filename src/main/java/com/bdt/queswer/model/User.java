package com.bdt.queswer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String displayName;

    private String aboutMe;

    private String avatarUrl;

    private Date birthDate;

    private Boolean gender;

    private int followCount = 0;

    private Integer voteCount;

    private int point = 0;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @EqualsAndHashCode.Exclude
    private Account account;

    @ManyToOne
    @JoinColumn(name = "addressId")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;

    @ManyToMany
    @JoinTable(name = "follows",
            joinColumns = @JoinColumn(name = "followingUserId"),
            inverseJoinColumns = @JoinColumn(name = "followedUserId")
    )
    @ToString.Exclude
    private List<User> followingUsers;

    @ManyToMany(mappedBy = "followingUsers")
    @ToString.Exclude
    private List<User> followedByUsers;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Vote> votes;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Badge> badges;
}
