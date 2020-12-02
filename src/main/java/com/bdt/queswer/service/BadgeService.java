package com.bdt.queswer.service;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.BadgeType;
import com.bdt.queswer.repository.BadgeRepository;
import com.bdt.queswer.repository.BadgeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BadgeService {
    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    BadgeTypeRepository badgeTypeRepository;

    public List<BadgeType> getBadgeTypeList() throws CustomException {
        return badgeTypeRepository.findAll();
    }
}
