package com.bdt.queswer.service;

import com.bdt.queswer.dto.*;
import com.bdt.queswer.dto.request.AddPostRequest;
import com.bdt.queswer.dto.request.EditPostRequest;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.Post;
import com.bdt.queswer.model.PostType;
import com.bdt.queswer.model.SubjectType;
import com.bdt.queswer.model.User;
import com.bdt.queswer.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    PostTypeRepository postTypeRepository;

    @Autowired
    ModelMapper mapper;

    public QuestionDto addNewQuestion(AddPostRequest request) throws CustomException {
        Post post = new Post();
        if (subjectRepository.existsById(request.getSubjectTypeId())) {
            post.setSubjectType(subjectRepository.findById(request.getSubjectTypeId()).get());
        } else {
            throw new CustomException("SubjectTypeId không hợp lệ");
        }
        if (gradeRepository.existsById(request.getGradeTypeId())) {
            post.setGradeType(gradeRepository.findById(request.getGradeTypeId()).get());
        } else {
            throw new CustomException("GradeTypeId không hợp lệ");
        }
        PostType postType = postTypeRepository.findByName("Question").get();
        post.setPostType(postType);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String email = authentication.getName();
        //System.out.println("ahih "+userRepository.findByAccountEmail(email));
        post.setUser(userRepository.findByAccountEmail(authentication.getName()).get());
        post.setCreationDate(new Date());
        post.setBody(request.getBody());
        post.setImgUrl(request.getImgUrl());
        Post newPost = postRepository.save(post);
        QuestionDto dto = mapper.map(newPost, QuestionDto.class);
        return dto;
    }

    public List<QuestionDto> getListQuestion(int limit, int pageNumber, String sortCrit) throws CustomException {
        List<QuestionDto> dtos = new ArrayList<>();
        Sort sort;
        switch (sortCrit) {
            case "+date":
                sort = Sort.by("creationDate").ascending();
                break;
            case "-date":
                sort = Sort.by("creationDate").descending();
                break;
            case "+view":
                sort = Sort.by("viewCount").ascending();
                break;
            case "-view":
                sort = Sort.by("viewCount").descending();
                break;
            case "+answer":
                sort = Sort.by("answerCount").ascending();
                break;
            case "-answer":
                sort = Sort.by("answerCount").descending();
                break;
            case "+vote":
                sort = Sort.by("voteCount").ascending();
                break;
            case "-vote":
                sort = Sort.by("voteCount").descending();
                break;
            default:
                throw new CustomException("Tham số sort không hợp lệ");
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, sort.and(Sort.by("id").ascending()));
        postRepository.findAllByPostTypeId(1, pageable).forEach(item -> {
            QuestionDto dto = mapper.map(item, QuestionDto.class);
            //dto.setUser(mapper.map(item.getUser(), UserDisplayDto.class));
            dtos.add(dto);
        });
        return dtos;
    }

    public QuestionDetailsDto getQuestionDetails(long id) throws CustomException {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()) {
            Post post = optional.get();
            if (!post.getPostType().getName().equals("Question"))
                throw new CustomException("Câu hỏi không tồn tại");
            QuestionDetailsDto dto = mapper.map(post, QuestionDetailsDto.class);
            //dto.getAnswers().forEach();
            return dto;
        } else {
            throw new CustomException("Câu hỏi này không tồn tại");
        }
    }

    public PostDto editPost(long id, EditPostRequest request) throws CustomException {
        Optional<Post> optional = postRepository.findById(id);
        System.out.println(request);
        if (optional.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            long userId = userRepository.findByAccountEmail(authentication.getName()).get().getId();
            Post post = optional.get();
            if (userId != post.getUser().getId()) {
                throw new CustomException("Truy cập không hợp lệ", HttpStatus.FORBIDDEN);
            }
            if (post.getPostType().getName().equals("Question")) {
                if (request.getSubjectTypeId() != null) {
                    if (request.getSubjectTypeId() != null && subjectRepository.existsById(request.getSubjectTypeId())) {
                        System.out.println("123");
                        post.setSubjectType(subjectRepository.findById(request.getSubjectTypeId()).get());
                    } else {
                        throw new CustomException("SubjectTypeId không hợp lệ");
                    }
                }
                if (request.getGradeTypeId() != null) {
                    if (gradeRepository.existsById(request.getGradeTypeId())) {
                        post.setGradeType(gradeRepository.findById(request.getGradeTypeId()).get());
                    } else {
                        throw new CustomException("GradeTypeId không hợp lệ");
                    }
                }
            }
            post.setBody(request.getBody());
            post.setLastEditDate(new Date());
            return mapper.map(postRepository.save(post), PostDto.class);
        } else {
            throw new CustomException("Bài đăng không tồn tại");
        }
    }

    public void deletePost(long id) throws CustomException {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()) {
            Post post = optional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Collection<SimpleGrantedAuthority> authorities =
                    (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
            long userId = userRepository.findByAccountEmail(authentication.getName()).get().getId();
            if (userId == post.getUser().getId() || authorities.contains(authority)) {
                postRepository.deleteById(id);
            } else {
                throw new CustomException("Truy cập không hợp lệ", HttpStatus.FORBIDDEN);
            }
        } else {
            throw new CustomException("Bài đăng không tồn tại");
        }

    }

    public AnswerDto addNewAnswer(AddPostRequest request) throws CustomException {
        Post post = new Post();
        Optional<Post> optionalQues = postRepository.findByIdAndPostTypeId(request.getParentId(), 1);
        if (!optionalQues.isPresent()) {
            throw new CustomException("Id câu hỏi không tồn tại");
        }
        post.setBody(request.getBody());
        post.setImgUrl(request.getImgUrl());
        post.setParentPost(optionalQues.get());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        post.setUser(userRepository.findByAccountEmail(authentication.getName()).get());
        post.setCreationDate(new Date());
        PostType postType = postTypeRepository.findByName("Answer").get();
        post.setPostType(postType);
        Post newPost = postRepository.save(post);
        AnswerDto dto = mapper.map(newPost, AnswerDto.class);
        return dto;
    }

    public List<QuestionDto> getListQuestionByUser(int limit, int pageNumber, String sortCrit, long userId)
            throws CustomException {
        List<QuestionDto> dtos = new ArrayList<>();
        Sort sort;
        switch (sortCrit) {
            case "+date":
                sort = Sort.by("creationDate").ascending();
                break;
            case "-date":
                sort = Sort.by("creationDate").descending();
                break;
            case "+view":
                sort = Sort.by("viewCount").ascending();
                break;
            case "-view":
                sort = Sort.by("viewCount").descending();
                break;
            case "+answer":
                sort = Sort.by("answerCount").ascending();
                break;
            case "-answer":
                sort = Sort.by("answerCount").descending();
                break;
            case "+vote":
                sort = Sort.by("voteCount").ascending();
                break;
            case "-vote":
                sort = Sort.by("voteCount").descending();
                break;
            default:
                throw new CustomException("Tham số sort không hợp lệ");
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, sort.and(Sort.by("id").ascending()));
        Page<Post> postPage = postRepository.findAllByPostTypeIdAndOwnerId(1, userId, pageable);
        if (pageNumber > postPage.getTotalPages()) {
            throw new CustomException("Tham số page vượt quá tổng số trang");
        }
        postPage.forEach(item -> {
            QuestionDto dto = mapper.map(item, QuestionDto.class);
            dtos.add(dto);
        });
        return dtos;
    }

    public List<AnswerDto> getListAnswerByUser(int limit, int pageNumber, String sortCrit, long userId)
            throws CustomException {
        List<AnswerDto> dtos = new ArrayList<>();
        Sort sort;
        switch (sortCrit) {
            case "+date":
                sort = Sort.by("creationDate").ascending();
                break;
            case "-date":
                sort = Sort.by("creationDate").descending();
                break;
            case "+vote":
                sort = Sort.by("voteCount").ascending();
                break;
            case "-vote":
                sort = Sort.by("voteCount").descending();
                break;
            default:
                throw new CustomException("Tham số sort không hợp lệ");
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, sort.and(Sort.by("id").ascending()));
        Page<Post> postPage = postRepository.findAllByPostTypeIdAndOwnerId(2, userId , pageable);
        if (pageNumber > postPage.getTotalPages()) {
            throw new CustomException("Tham số page vượt quá tổng số trang");
        }
        postPage.forEach(item -> {
            AnswerDto dto = mapper.map(item, AnswerDto.class);
            dtos.add(dto);
        });
        return dtos;
    }
}
