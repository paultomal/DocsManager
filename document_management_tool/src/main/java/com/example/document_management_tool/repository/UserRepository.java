package com.example.document_management_tool.repository;

import com.example.document_management_tool.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);

    Optional<UserInfo> findByEmail(String email);


}
