package com.bdt.queswer.dto;

import com.bdt.queswer.model.BadgeType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BadgeDto {
    private long id;

    private Date creationDate;

    private String typeName;

    private String typeColor;
}
