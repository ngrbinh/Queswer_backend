package com.bdt.queswer.dto;

import com.bdt.queswer.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class QuestionDetailsDto {
    private long id;

    private Date creationDate;

    private Date lastEditDate;

    private String body;

    private String imgUrl;

    private int answerCount;

    private int voteCount;

    private int viewCount;

    private String subjectTypeName;

    private String gradeTypeName;

    private long subjectTypeId;

    private long gradeTypeId;

    private List<AnswerDto> answers;

    private UserDisplayDto user;
}
