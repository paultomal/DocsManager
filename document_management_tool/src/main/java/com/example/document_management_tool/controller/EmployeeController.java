package com.example.document_management_tool.controller;

import com.example.document_management_tool.dto.ChangePassword;
import com.example.document_management_tool.dto.UserInfoDTO;
import com.example.document_management_tool.entity.UserInfo;
import com.example.document_management_tool.enums.UserRoles;
import com.example.document_management_tool.exceptions.EmailAlreadyTakenException;
import com.example.document_management_tool.exceptions.IllegalActionException;
import com.example.document_management_tool.exceptions.UserIsNotFoundException;
import com.example.document_management_tool.exceptions.UserNameAlreadyTakenException;
import com.example.document_management_tool.security.JwtAuthFilter;
import com.example.document_management_tool.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/employee")

public class EmployeeController {
    private final UserServices userServices;
    private final JwtAuthFilter jwtAuthFilter;


    public EmployeeController(UserServices userServices, JwtAuthFilter jwtAuthFilter) {
        this.userServices = userServices;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT','ROLE_EMPLOYEE')")
    @PostMapping("/addEmployee")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody UserInfoDTO userInfoDTO) throws EmailAlreadyTakenException, UserNameAlreadyTakenException {
        if (userServices.getUserByEmail(userInfoDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException(userInfoDTO.getEmail() + " is already registered!!! Try Another");
        }
        if (userServices.getUserByUserName(userInfoDTO.getUsername()).isPresent()) {
            throw new UserNameAlreadyTakenException(userInfoDTO.getUsername() + "is already registered!! Try Another");
        }
        UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.saveEmployee(userInfoDTO));
        return new ResponseEntity<>(userInfoDTO1, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ROOT')")
    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees() {
        List<UserInfo> userInfos = userServices.getAllEmployees();
        List<UserInfoDTO> admin = userInfos.stream()
                .map(UserInfoDTO::form)
                .filter(u -> u.getRoles().equals(UserRoles.getLabelByUserRoles(UserRoles.ROLE_EMPLOYEE)))
                .toList();
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT','ROLE_SUPERVISOR','ROLE_EMPLOYEE')")
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

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT','ROLE_EMPLOYEE')")
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody UserInfoDTO userInfoDTO, @PathVariable Long id) {
        UserInfo user = userServices.getEmployeeById(id);

        if (user != null && user.getRoles().equals(UserRoles.ROLE_EMPLOYEE)) {
            UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.updateEmployee(userInfoDTO, id));
            return new ResponseEntity<>(userInfoDTO1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT','ROLE_EMPLOYEE')")
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        UserInfo user = userServices.getEmployeeById(id);

        if (user != null && user.getRoles().equals(UserRoles.ROLE_EMPLOYEE)) {
            Boolean deleted = userServices.deleteEmployee(id);
            if (deleted) {
                return new ResponseEntity<>("Employee " + id + " is deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword, @PathVariable Long id) throws IllegalActionException, UserIsNotFoundException {
        if(userServices.getEmployeeById(id) == null)
            throw new UserIsNotFoundException("There is no student with ID " + id + "!!!" + "Try Again");

        if(Objects.equals(jwtAuthFilter.getCurrentUser(), userServices.getEmployeeById(id).getEmail()) || jwtAuthFilter.isEmployee()) {
            String message = userServices.changeEmployeePassword(changePassword, id);
            if(message.equalsIgnoreCase("Password Changed")){
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        throw new IllegalActionException("You can not change other employee's password!!!");
    }
}
