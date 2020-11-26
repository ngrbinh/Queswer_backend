package com.bdt.queswer.controller;

import com.bdt.queswer.dto.NotificationPageDto;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/user")
    public NotificationPageDto getAllByUser(
            @RequestParam(name = "limit",defaultValue = "10") int limit,
            @RequestParam(name = "page",defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by",defaultValue = "-date") String sortCrit
    ) throws CustomException{
        if (limit < 1 || pageNumber < 1) {
            throw new CustomException("Tham số limit và page ko được nhỏ hơn 1");
        }
        return notificationService.getListByUser(limit,pageNumber,sortCrit);
    }

    @GetMapping("/checked")
    public ResponseEntity<String> checkAllNotification() throws CustomException{
        notificationService.allNotificationChecked();
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
