package com.bdt.queswer.controller;

import com.bdt.queswer.dto.UserPageDto;
import com.bdt.queswer.dto.request.ChangePasswordRequest;
import com.bdt.queswer.dto.request.ChangeProfileRequest;
import com.bdt.queswer.dto.request.SignUpRequest;
import com.bdt.queswer.dto.UserDto;
import com.bdt.queswer.dto.UserProfileDto;
import com.bdt.queswer.dto.request.VoteRequest;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.service.MyUserDetailsService;
import com.bdt.queswer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @GetMapping("/all")
    public UserPageDto getListUser(
            @RequestParam(name = "limit", defaultValue = "30") int limit,
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = "-point") String sortCrit) throws CustomException {
        if (limit < 1 || pageNumber < 1) {
            throw new CustomException("Tham số limit và page ko được nhỏ hơn 1");
        }
        return userService.getListUser(limit, pageNumber, sortCrit);
    }

    @PostMapping("/signup")
    public UserDto signUp(
            @RequestBody @Valid SignUpRequest signUpRequest) throws CustomException {
        UserDto dto = userService.createNewUser(signUpRequest, "USER");
        return dto;
    }

    @GetMapping("/profile")
    public UserProfileDto getOwnProfile() throws CustomException {
        System.out.println("hic");
        return userService.getOwnDetails();
    }

    @GetMapping("/{id}")
    public UserProfileDto getProfile(@PathVariable(name = "id") long id) throws Exception {
        return userService.getUserDetail(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> changeProfile(
            @PathVariable(name = "id") long id,
            @RequestBody @Valid ChangeProfileRequest req) throws CustomException {
        userService.editUser(req, id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable(name = "id") long id
    ) throws CustomException {
        userService.deleteUser(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request
    ) throws CustomException {
        userDetailsService.changePassword(request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<String> follow(
            @PathVariable(name = "id") long id
    ) throws CustomException {
        userService.follow(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/unfollow/{id}")
    public ResponseEntity<String> unFollow(
            @PathVariable(name = "id") long id
    ) throws CustomException {
        userService.unFollow(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/vote")
    public ResponseEntity<String> vote(
            @RequestBody VoteRequest request
    ) throws CustomException {
        int value = userService.vote(request);
        return new ResponseEntity<>(Integer.toString(value), HttpStatus.OK);
    }
}
