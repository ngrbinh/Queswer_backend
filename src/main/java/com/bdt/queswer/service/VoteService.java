package com.bdt.queswer.service;

import com.bdt.queswer.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    @Autowired
    VoteRepository voteRepository;
}
