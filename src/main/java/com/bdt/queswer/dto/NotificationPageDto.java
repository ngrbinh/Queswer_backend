package com.bdt.queswer.dto;

import com.bdt.queswer.model.Notification;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotificationPageDto {
    private int totalPage;

    List<NotificationDto> notifications;
}
