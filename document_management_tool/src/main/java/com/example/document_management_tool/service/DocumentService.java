package com.example.document_management_tool.service;

import com.example.document_management_tool.dto.DocumentDTO;
import com.example.document_management_tool.entity.Document;
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
    public Document saveDocument(DocumentDTO documentDTO) {

        Optional<UserInfo> userInfo = userRepository.findByUsername(documentDTO.getUsername());
        Document document = new Document();

        if (userInfo.isPresent()) {

            UserInfo userName = userRepository.findByUsername(documentDTO.getUsername()).orElseThrow(RuntimeException::new);
            document.setUserInfo(userName);
            document.setSubject(documentDTO.getSubject());
            document.setMessage(documentDTO.getMessage());

            Priority priority = Priority.getPriorityFromString(documentDTO.getPriority());
            document.setPriority(priority);

            documentRepository.save(document);
        }
        return document;
    }


    public List<Document> getAllDocuments() {
            return documentRepository.findAll();
    }

    public Document updateDocument(DocumentDTO documentDTO, Long id) {
        Optional<Document> document = documentRepository.findById(id);

        if (document.isPresent()){
            Document document1 = document.get();
            document1.setSubject(documentDTO.getSubject());
            document1.setMessage(documentDTO.getMessage());

            Priority priority = Priority.getPriorityFromString(documentDTO.getPriority());
            document1.setPriority(priority);

            return documentRepository.save(document1);
        }
        return null;
    }

    public Boolean deleteDocument(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            documentRepository.delete(document.get());
            return true;
        }
        return false;
    }
}
