package com.bdt.queswer.controller;

import com.bdt.queswer.dto.SignUpRequest;
import com.bdt.queswer.dto.UserDto;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.User;
import com.bdt.queswer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDto> getListUser() {
        return userService.getListUser();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @RequestBody @Valid SignUpRequest signUpRequest) throws CustomException {
        userService.createNewUser(signUpRequest,"USER");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/detail")
    public ResponseEntity<String> changeProfile(@RequestBody @Valid UserDto dto) {
        userService.editUser(dto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
