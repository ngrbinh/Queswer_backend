package com.bdt.queswer.service;

import com.bdt.queswer.dto.SignUpRequest;
import com.bdt.queswer.dto.UserDto;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.Account;
import com.bdt.queswer.model.Role;
import com.bdt.queswer.model.User;
import com.bdt.queswer.repository.RoleRepository;
import com.bdt.queswer.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public List<UserDto> getListUser() {
        List<UserDto> dtos = new ArrayList<>();
        userRepository.findAll().forEach(item -> {
            dtos.add(modelMapper.map(item,UserDto.class));
        });
        return dtos;
    }

    public void createNewUser(SignUpRequest signUpRequest,String roleName) throws CustomException {
        User user = modelMapper.map(signUpRequest,User.class);
        Account account = modelMapper.map(signUpRequest, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new CustomException("Role "+roleName+" doesn't exist");
        }
        else account.setRole(role);
        user.setAccount(account);
        userRepository.save(user);
    }

    public void editUser(UserDto dto) {
        userRepository.save(modelMapper.map(dto, User.class));
    }
}
