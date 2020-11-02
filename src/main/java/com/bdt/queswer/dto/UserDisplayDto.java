package com.bdt.queswer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
@ToString
public class UserDisplayDto {
    @Nullable
    private long id;

    private String displayName;

    private String avatarUrl;

    private List<BadgeDto> badges;
}
