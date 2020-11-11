package com.bdt.queswer.controller;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.SubjectType;
import com.bdt.queswer.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @GetMapping("/all")
    public List<SubjectType> getListSubject() throws CustomException {
        return subjectService.getListSubject();
    }
}

