package com.bdt.queswer.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;

    private Long postId;

    @NotNull
    private Date creationDate;

    @NotNull
    private Boolean checked;

    @ManyToOne
    @JoinColumn(name = "typeId")
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "userId",insertable = false,updatable = false)
    private long userId;
}
