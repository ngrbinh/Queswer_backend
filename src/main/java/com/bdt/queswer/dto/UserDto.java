package com.bdt.queswer.dto;

import com.bdt.queswer.model.Badge;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDto {
    @Nullable
    private long id;

    private String displayName;

    private String aboutMe;

    private String avatarUrl;

    private Date birthDate;

    private boolean gender;

    private int followCount;

    private int voteCount;

    private Integer answerCount;

    private Integer questionCount;

    private String phoneNumber;

    private List<BadgeDto> badges;
}
