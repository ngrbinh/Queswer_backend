package com.bdt.queswer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AnswerDto {
    private Long id;

    private String body;

    private String imgUrl;

    private int voteCount;

    private long parentPostId;

    private Date creationDate;

    private Date lastEditDate;

    private UserDisplayDto user;
}
