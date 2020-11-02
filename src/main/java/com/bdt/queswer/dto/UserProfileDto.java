package com.bdt.queswer.dto;

import com.bdt.queswer.model.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserProfileDto {
    private long id;

    private String displayName;

    private String aboutMe;

    private String avatarUrl;

    private Date birthDate;

    private boolean gender;

    private Address address;

    private int followCount;

    private int voteCount;

    private int point;

    private String phoneNumber;

    @ToString.Exclude
    private AccountDto account;

    @ToString.Exclude
    private List<UserDisplayDto> followingUsers;

    @ToString.Exclude
    private List<UserDisplayDto> followedByUsers;

    private List<BadgeDto> badges;
}
