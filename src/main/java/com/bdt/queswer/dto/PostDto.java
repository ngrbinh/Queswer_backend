package com.bdt.queswer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDto {
    private long id;

    private long userId;

    private Date creationDate;

    private Date lastEditDate;

    private String body;

    private String subjectTypeName;

    private String gradeTypeName;
}
