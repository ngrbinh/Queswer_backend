package com.bdt.queswer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
public class UserDto {
    private long id;

    private String displayName;

    private String aboutMe;

    private String avatarUrl;

    private Date birthDate;

    private boolean gender;
}
