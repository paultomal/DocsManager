package com.example.document_management_tool.service;

import com.example.document_management_tool.dto.UserInfoDTO;
import com.example.document_management_tool.entity.UserInfo;
import com.example.document_management_tool.enums.UserRoles;
import com.example.document_management_tool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServices {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //Supervisor

    public UserInfo saveSupervisor(UserInfoDTO userInfoDTO) {


        UserInfo userInfo = new UserInfo();

        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        userInfo.setName(userInfoDTO.getName());
        userInfo.setUsername(userInfoDTO.getUsername().toLowerCase());
        userInfo.setEmail(userInfoDTO.getEmail());
        userInfo.setPassword(userInfoDTO.getPassword());
        userInfo.setContact(userInfoDTO.getContact());
        UserRoles userRoles = UserRoles.getUserRolesByLabel("Supervisor");
        userInfo.setRoles(userRoles);
        userRepository.save(userInfo);

        return userInfo;
    }


    public List<UserInfo> getAllSupervisors() {
        return userRepository.findAll();
    }


    public UserInfo getSupervisorById(Long id) {
        Optional<UserInfo> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public UserInfo updateSupervisor(UserInfoDTO userInfoDTO, Long id) {

        Optional<UserInfo> userInfo = userRepository.findById(id);

        if (userInfo.isPresent()) {
            userInfo.get().setName(userInfoDTO.getName());
            userInfo.get().setUsername(userInfoDTO.getUsername().toLowerCase());
            userInfo.get().setEmail(userInfoDTO.getEmail());
            userInfo.get().setContact(userInfoDTO.getContact());
            return userRepository.save(userInfo.get());
        }
        return null;
    }

    public Boolean deleteSupervisor(Long id) {
        Optional<UserInfo> userInfo = userRepository.findById(id);
        if (userInfo.isPresent()) {
            userRepository.delete(userInfo.get());
            return true;
        }
        return false;
    }

    //employee


    public UserInfo saveEmployee(UserInfoDTO userInfoDTO) {

        UserInfo userInfo = new UserInfo();

        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        userInfo.setName(userInfoDTO.getName());
        userInfo.setUsername(userInfoDTO.getUsername().toLowerCase());
        userInfo.setEmail(userInfoDTO.getEmail());
        userInfo.setPassword(userInfoDTO.getPassword());
        userInfo.setContact(userInfoDTO.getContact());
        UserRoles userRoles = UserRoles.getUserRolesByLabel("Employee");
        userInfo.setRoles(userRoles);
        userRepository.save(userInfo);

        return userInfo;
    }


    public List<UserInfo> getAllEmployees() {
        return userRepository.findAll();
    }


    public UserInfo getEmployeeById(Long id) {
        Optional<UserInfo> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public UserInfo updateEmployee(UserInfoDTO userInfoDTO, Long id) {

        Optional<UserInfo> userInfo = userRepository.findById(id);

        if (userInfo.isPresent()) {
            userInfo.get().setName(userInfoDTO.getName());
            userInfo.get().setUsername(userInfoDTO.getUsername().toLowerCase());
            userInfo.get().setEmail(userInfoDTO.getEmail());
            userInfo.get().setContact(userInfoDTO.getContact());
            return userRepository.save(userInfo.get());
        }
        return null;
    }

    public Boolean deleteEmployee(Long id) {
        Optional<UserInfo> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }
        return false;
    }

    public Optional<UserInfo> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserInfo> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

}
