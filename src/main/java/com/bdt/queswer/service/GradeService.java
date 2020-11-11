package com.bdt.queswer.service;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.GradeType;
import com.bdt.queswer.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    GradeRepository gradeRepository;

    public List<GradeType> getListGrade() throws CustomException {
        try {
            return gradeRepository.findAll();
        } catch (Error e) {
            throw  new CustomException("Tải dữ liệu không thành công");
        }
    }
}