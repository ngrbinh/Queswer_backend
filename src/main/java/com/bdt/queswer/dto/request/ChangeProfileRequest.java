package com.bdt.queswer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
@Setter
public class ChangeProfileRequest {
    private String displayName;

    @Nullable
    private String aboutMe;

    @Nullable
    private String avatarUrl;

    @Nullable
    private Date birthDate;

    @Nullable
    private boolean gender;

    @Nullable
    private Long addressId;

    @Nullable
    private String phoneNumber;
}
