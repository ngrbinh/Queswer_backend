package com.bdt.queswer.service;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.SubjectType;
import com.bdt.queswer.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    public List<SubjectType> getListSubject() throws CustomException {
        try {
            return subjectRepository.findAll();
        } catch (Error e) {
            throw  new CustomException("Tải dữ liệu không thành công");
        }
    }
}