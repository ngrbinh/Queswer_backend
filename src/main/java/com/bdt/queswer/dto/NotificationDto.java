package com.bdt.queswer.dto;

import com.bdt.queswer.model.NotificationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class NotificationDto {
    private long id;

    private String message;

    private Long postId;

    private Date creationDate;

    private Boolean checked;

    private String typeName;
}
