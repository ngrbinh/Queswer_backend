package com.bdt.queswer.service;

import com.bdt.queswer.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BadgeService {
    @Autowired
    BadgeRepository badgeRepository;
}
