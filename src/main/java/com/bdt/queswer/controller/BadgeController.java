package com.bdt.queswer.controller;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.BadgeType;
import com.bdt.queswer.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/badge")
public class BadgeController {
    @Autowired
    private BadgeService badgeService;

    @GetMapping("/types")
    public List<BadgeType> getBadgeTypeList() throws CustomException {
        return badgeService.getBadgeTypeList();
    }
}
