package com.bdt.queswer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPageDto {
    private int totalPage;

    List<UserDto> users;
}
