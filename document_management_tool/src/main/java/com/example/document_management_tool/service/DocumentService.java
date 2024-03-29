package com.example.document_management_tool.service;

import com.example.document_management_tool.dto.DocumentDTO;
import com.example.document_management_tool.entity.Documents;
import com.example.document_management_tool.entity.UserInfo;
import com.example.document_management_tool.enums.Priority;
import com.example.document_management_tool.repository.DocumentRepository;
import com.example.document_management_tool.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Documents saveDocument(DocumentDTO documentDTO) {
        UserInfo userInfo = userRepository.findByUsername(documentDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Documents documents = new Documents();
        documents.setUserInfo(userInfo);
        documents.setSubject(documentDTO.getSubject());
        documents.setMessage(documentDTO.getMessage());

        Priority priority = Priority.getPriorityFromString(documentDTO.getPriority());
        documents.setPriority(priority);

        documentRepository.save(documents);
        return documents;
    }


    public List<Documents> getAllDocuments() {
            return documentRepository.findAll();
    }



    public Documents updateDocument(DocumentDTO documentDTO, Long id) {
        Optional<Documents> document = documentRepository.findById(id);

        if (document.isPresent()){
            Documents documents1 = document.get();
            documents1.setSubject(documentDTO.getSubject());
            documents1.setMessage(documentDTO.getMessage());

            Priority priority = Priority.getPriorityFromString(documentDTO.getPriority());
            documents1.setPriority(priority);

            return documentRepository.save(documents1);
        }
        return null;
    }

    public Boolean deleteDocument(Long id) {
        Optional<Documents> document = documentRepository.findById(id);
        if (document.isPresent()) {
            documentRepository.delete(document.get());
            return true;
        }
        return false;
    }

    public Documents getDocumentById(Long id) {
        Optional<Documents> documents = documentRepository.findById(id);
        return documents.orElseThrow();
    }
}
