package com.bdt.queswer.controller;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.GradeType;
import com.bdt.queswer.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/grade")
public class GradeController {
    @Autowired
    GradeService gradeService;

    @GetMapping("/all")
    public List<GradeType> getListGrade() throws CustomException {
        return gradeService.getListGrade();
    }
}

