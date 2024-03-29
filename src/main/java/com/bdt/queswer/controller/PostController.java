package com.bdt.queswer.controller;

import com.bdt.queswer.dto.*;
import com.bdt.queswer.dto.request.AddPostRequest;
import com.bdt.queswer.dto.request.EditPostRequest;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/question")
    public QuestionDto addQuestion(
            @RequestBody AddPostRequest request) throws CustomException {
        if (request.getSubjectTypeId() == null || request.getGradeTypeId() == null) {
            throw new CustomException("Thiếu dữ liệu");
        }
        return postService.addNewQuestion(request);
    }

    @GetMapping("/question/all")
    public QuestionPageDto getListQuestion(
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = "-date") String sortCrit,
            @RequestParam(name = "subject_id", required = false) Long subjectTypeId,
            @RequestParam(name = "grade_id",required = false) Long gradeTypeId,
            @RequestParam(name = "body", defaultValue = "") String body) throws CustomException {
        if (limit < 1 || pageNumber < 1) {
            throw new CustomException("Tham số limit và page ko được nhỏ hơn 1");
        }
        return postService.getListQuestion(limit, pageNumber, sortCrit,subjectTypeId,gradeTypeId,body);
    }

    @GetMapping("/answer/all")
    public AnswerPageDto getListAnswer(
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = "-date") String sortCrit) throws CustomException {
        if (limit < 1 || pageNumber < 1) {
            throw new CustomException("Tham số limit và page ko được nhỏ hơn 1");
        }
        return postService.getListAnswer(limit, pageNumber, sortCrit);
    }

    @GetMapping("/question/{id}")
    public QuestionDetailsDto getQuestionDetails(
            @PathVariable(name = "id") long id
    ) throws CustomException {
        return postService.getQuestionDetails(id);
    }

    @GetMapping("/question/user/{id}")
    public List<QuestionDto> getListQuestionByUser(
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = "-date") String sortCrit,
            @PathVariable(name = "id") long id) throws CustomException {
        if (limit < 1 || pageNumber < 1) {
            throw new CustomException("Tham số limit và page ko được nhỏ hơn 1");
        }
        return postService.getListQuestionByUser(limit, pageNumber, sortCrit, id);
    }

    @GetMapping("/answer/user/{id}")
    public List<AnswerDto> getListAnswerByUser(
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = "-date") String sortCrit,
            @PathVariable(name = "id") long id) throws CustomException {
        if (limit < 1 || pageNumber < 1) {
            throw new CustomException("Tham số limit và page ko được nhỏ hơn 1");
        }
        return postService.getListAnswerByUser(limit, pageNumber, sortCrit, id);
    }

    @PostMapping("/{id}")
    public PostDto editPost(
            @PathVariable(name = "id") long id,
            @RequestBody EditPostRequest request
    ) throws CustomException {
        return postService.editPost(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable(name = "id") long id
    ) throws CustomException {
        postService.deletePost(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/answer")
    public AnswerDto addAnswer(
            @RequestBody AddPostRequest request
    ) throws CustomException {
        if (request.getParentId() == null) {
            throw new CustomException("Thiếu id câu hỏi");
        }
        return postService.addNewAnswer(request);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<String> addView(
            @PathVariable(name = "id") long postId
    ) {
        postService.addView(postId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/question/ids")
    public List<QuestionDto> getQuestionsByIds(
            @RequestBody List<Long> ids
    ) throws CustomException{
        return  postService.getListQuestionByIds(ids);
    }
}
