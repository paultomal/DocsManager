package com.example.document_management_tool.controller;

import com.example.document_management_tool.dto.UserInfoDTO;
import com.example.document_management_tool.entity.UserInfo;
import com.example.document_management_tool.enums.UserRoles;
import com.example.document_management_tool.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final UserServices userServices;

    public EmployeeController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody UserInfoDTO userInfoDTO) /*throws EmailAlreadyTakenException, UserNameAlreadyTakenException*/ {
/*        if(userServices.getUserByEmail(userInfoDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException(userInfoDTO.getEmail() + " is already registered!!! Try Another");
        }
        if (userServices.getUserByUserName(userInfoDTO.getUsername()).isPresent()) {
            throw new UserNameAlreadyTakenException(userInfoDTO.getUsername() + "is already registered!! Try Another");
        }*/
        UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.saveEmployee(userInfoDTO));
        return new ResponseEntity<>(userInfoDTO1, HttpStatus.OK);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees() {
        List<UserInfo> userInfos = userServices.getAllEmployees();
        List<UserInfoDTO> admin = userInfos.stream()
                .map(UserInfoDTO::form)
                .filter(u -> u.getRoles().equals(UserRoles.getLabelByUserRoles(UserRoles.ROLE_EMPLOYEE)))
                .toList();
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        UserInfo user = userServices.getEmployeeById(id);

        if (user != null && user.getRoles().equals(UserRoles.ROLE_EMPLOYEE)) {
            UserInfoDTO userInfoDTO = UserInfoDTO.form(user);
            return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody UserInfoDTO userInfoDTO, @PathVariable Long id) /*throws UserIsNotFoundException*/ {
/*     if (userServices.getSupervisorById(id) == null){
            throw new UserIsNotFoundException("Employee "+ id + " is not Found.");
        }*/
        UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.updateEmployee(userInfoDTO, id));
        return new ResponseEntity<>(userInfoDTO1, HttpStatus.OK);
    }

}
