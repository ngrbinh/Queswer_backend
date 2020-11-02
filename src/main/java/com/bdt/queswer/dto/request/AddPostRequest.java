package com.bdt.queswer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class AddPostRequest {
    private String body;

    @Nullable
    private Long parentId;

    @Nullable
    private Long gradeTypeId;

    @Nullable
    private Long subjectTypeId;

    @Nullable
    private String imgUrl;
}
