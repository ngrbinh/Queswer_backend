package com.bdt.queswer.service;

import com.bdt.queswer.dto.MetaData;
import com.bdt.queswer.repository.PostRepository;
import com.bdt.queswer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetaService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public MetaData getMetaData() {
        MetaData data = new MetaData();
        data.setUserCount((int) userRepository.count());
        data.setQuestionCount((int) postRepository.countByPostTypeId(1));
        data.setAnswerCount((int) postRepository.countByPostTypeId(2));
        data.setViewCount(postRepository.sumViewCount());
        return data;
    }
}
