package com.bdt.queswer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswerPageDto {
    private int totalPage;

    private List<AnswerDto> answers;
}
