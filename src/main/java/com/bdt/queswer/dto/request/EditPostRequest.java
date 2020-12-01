package com.bdt.queswer.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@Setter
@ToString
public class EditPostRequest {
    private String body;

    @Nullable
    private Long subjectTypeId;

    @Nullable
    private Long gradeTypeId;

    @Nullable
    private String imgUrl;
}
