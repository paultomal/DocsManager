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
@RequestMapping("/supervisor")

public class SupervisorController {
    private final UserServices userServices;
    private final JwtAuthFilter jwtAuthFilter;




    public SupervisorController(UserServices userServices, JwtAuthFilter jwtAuthFilter) {
        this.userServices = userServices;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT','ROLE_SUPERVISOR')")
    @PostMapping("/addSupervisor")
    public ResponseEntity<?> saveSupervisor(@Valid @RequestBody UserInfoDTO userInfoDTO) throws UserNameAlreadyTakenException, EmailAlreadyTakenException {
        if (userServices.getUserByEmail(userInfoDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException(userInfoDTO.getEmail() + " is already registered!!! Try Another");
        }
        if (userServices.getUserByUserName(userInfoDTO.getUsername()).isPresent()) {
            throw new UserNameAlreadyTakenException(userInfoDTO.getUsername() + "is already registered!! Try Another");
        }
        UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.saveSupervisor(userInfoDTO));
        return new ResponseEntity<>(userInfoDTO1, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ROOT')")
    @GetMapping("/getAllSupervisors")
    public ResponseEntity<?> getAllSupervisor() {
        List<UserInfo> userInfos = userServices.getAllSupervisors();
        List<UserInfoDTO> admin = userInfos.stream()
                .map(UserInfoDTO::form)
                .filter(u -> u.getRoles().equals(UserRoles.getLabelByUserRoles(UserRoles.ROLE_SUPERVISOR)))
                .toList();
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT','ROLE_SUPERVISOR','ROLE_EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupervisorById(@PathVariable Long id) {
        UserInfo user = userServices.getSupervisorById(id);

        if (user != null && user.getRoles().equals(UserRoles.ROLE_SUPERVISOR)) {
            UserInfoDTO userInfoDTO = UserInfoDTO.form(user);
            return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Supervisor not found", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT','ROLE_SUPERVISOR')")
    @PutMapping("/updateSupervisor/{id}")
    public ResponseEntity<?> updateSupervisor(@Valid @RequestBody UserInfoDTO userInfoDTO, @PathVariable Long id) {

        UserInfo user = userServices.getSupervisorById(id);

        if (user != null && user.getRoles().equals(UserRoles.ROLE_SUPERVISOR)) {
            UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.updateSupervisor(userInfoDTO, id));
            return new ResponseEntity<>(userInfoDTO1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Supervisor not found", HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT','ROLE_SUPERVISOR')")
    @DeleteMapping("/deleteSupervisor/{id}")
    public ResponseEntity<?> deleteSupervisor(@PathVariable Long id) {


        UserInfo userInfo = userServices.getSupervisorById(id);
        if (userInfo != null && userInfo.getRoles().equals(UserRoles.ROLE_SUPERVISOR)) {
            Boolean deleted = userServices.deleteSupervisor(id);
            if (deleted) {
                return new ResponseEntity<>("Supervisor " + id + " is deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Supervisor not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Supervisor not found", HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword, @PathVariable Long id) throws IllegalActionException, UserIsNotFoundException {
        if(userServices.getSupervisorById(id) == null)
            throw new UserIsNotFoundException("There is no student with ID " + id + "!!!" + "Try Again");

        if(Objects.equals(jwtAuthFilter.getCurrentUser(), userServices.getSupervisorById(id).getEmail()) || jwtAuthFilter.isSupervisor()) {
            String message = userServices.changePassword(changePassword, id);
            if(message.equalsIgnoreCase("Password Changed")){
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        throw new IllegalActionException("You can not change other supervisor's password!!!");
    }


}
