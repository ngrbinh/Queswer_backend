package com.bdt.queswer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class EditPostRequest {
    private String body;

    @Nullable
    private Long subjectTypeId;

    @Nullable
    private Long gradeTypeId;
}
