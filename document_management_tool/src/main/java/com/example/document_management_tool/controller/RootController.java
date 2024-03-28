package com.example.document_management_tool.controller;

import com.example.document_management_tool.dto.UserInfoDTO;
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
    public ResponseEntity<?> save(@Valid @RequestBody UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.saveSuperAdmin(userInfoDTO));
        return new ResponseEntity<>(userInfoDTO1, HttpStatus.CREATED);
    }


}
