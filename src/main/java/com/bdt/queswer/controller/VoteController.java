package com.bdt.queswer.controller;

import com.bdt.queswer.repository.VoteRepository;
import com.bdt.queswer.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    VoteService voteService;
}
