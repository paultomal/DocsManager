package com.example.document_management_tool.repository;

import com.example.document_management_tool.entity.Document;
import com.example.document_management_tool.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
