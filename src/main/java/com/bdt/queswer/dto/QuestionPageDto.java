package com.bdt.queswer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionPageDto {
    private int totalPage;

    List<QuestionDto> questions;
}
