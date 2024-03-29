package com.example.document_management_tool.controller;

import com.example.document_management_tool.dto.DocumentDTO;
import com.example.document_management_tool.dto.UserInfoDTO;
import com.example.document_management_tool.entity.Documents;
import com.example.document_management_tool.entity.UserInfo;
import com.example.document_management_tool.enums.UserRoles;
import com.example.document_management_tool.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/saveDocument")
    public ResponseEntity<?> saveDocument(@Valid @RequestBody DocumentDTO documentDTO){
        DocumentDTO documentDTO1 = DocumentDTO.form(documentService.saveDocument(documentDTO));
        return new ResponseEntity<>(documentDTO1, HttpStatus.OK);
    }

    @GetMapping("/getAllDocuments")
    public ResponseEntity<?> getAllDocuments() {

        List<Documents> documents = documentService.getAllDocuments();
        List<DocumentDTO> documentDTOS = documents.stream().map(DocumentDTO::form).toList();

        return new ResponseEntity<>(documentDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentById(@PathVariable Long id) {
        Documents documents = documentService.getDocumentById(id);

        if (documents != null) {
            DocumentDTO documentDTO = DocumentDTO.form(documents);
            return new ResponseEntity<>(documentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateDocument/{id}")
    public ResponseEntity<?> updateDocument(@Valid @RequestBody DocumentDTO documentDTO, @PathVariable Long id){
        DocumentDTO documentDTO1 = DocumentDTO.form(documentService.updateDocument(documentDTO,id));
        return new ResponseEntity<>(documentDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocument/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        Boolean deleted = documentService.deleteDocument(id);
        if (deleted) {
            return new ResponseEntity<>("Document " + id +" is deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
        }
    }

}
