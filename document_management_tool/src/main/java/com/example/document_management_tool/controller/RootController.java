package com.example.document_management_tool.controller;

import com.example.document_management_tool.dto.UserInfoDTO;
import com.example.document_management_tool.exceptions.EmailAlreadyTakenException;
import com.example.document_management_tool.exceptions.UserNameAlreadyTakenException;
import com.example.document_management_tool.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rootAdmin")
public class RootController {
    private final UserServices userServices;

    public RootController(UserServices userServices) {
        this.userServices = userServices;
    }
    @PostMapping("/addRoot")
    public ResponseEntity<?> save(@Valid @RequestBody UserInfoDTO userInfoDTO) throws EmailAlreadyTakenException, UserNameAlreadyTakenException {
        if(userServices.getUserByEmail(userInfoDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException(userInfoDTO.getEmail() + " is already registered!!! Try Another");
        }
        if (userServices.getUserByUserName(userInfoDTO.getUsername()).isPresent()) {
            throw new UserNameAlreadyTakenException(userInfoDTO.getUsername() + "is already registered!! Try Another");
        }
        UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.saveSuperAdmin(userInfoDTO));
        return new ResponseEntity<>(userInfoDTO1, HttpStatus.CREATED);
    }


}
