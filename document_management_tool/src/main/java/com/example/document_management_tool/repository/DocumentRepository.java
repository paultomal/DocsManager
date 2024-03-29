package com.example.document_management_tool.repository;

import com.example.document_management_tool.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Documents, Long> {
}
