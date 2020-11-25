package com.bdt.queswer.controller;

import com.bdt.queswer.dto.MetaData;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meta")
public class MetaController {
    @Autowired
    MetaService metaService;

    @GetMapping("")
    public MetaData get() throws CustomException {
        return metaService.getMetaData();
    }
}
