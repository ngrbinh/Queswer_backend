package com.bdt.queswer.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VoteRequest {
    private Long postId;
    @NotEmpty
    @NotNull
    private boolean voteType;
}
