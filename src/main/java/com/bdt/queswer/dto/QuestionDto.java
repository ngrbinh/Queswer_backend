package com.bdt.queswer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
@Setter
public class QuestionDto {
    private long id;

    private String body;

    private String imgUrl;

    private Date creationDate;

    private int voteCount;

    private int answerCount;

    private int viewCount;

    private String gradeTypeName;

    private String subjectTypeName;

    private UserDisplayDto user;
}
