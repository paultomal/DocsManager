package com.example.document_management_tool.dto;

import com.example.document_management_tool.entity.Document;
import com.example.document_management_tool.enums.Priority;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {
    private Long id;

    @NotEmpty(message = "Username should not be empty")
    private String username;

    @NotEmpty(message = "Subject should not be empty")
    private String subject;

    @NotEmpty(message = "Message should not be empty")
    private String message;

    private String priority;
    public static DocumentDTO form(Document document){
        if(document == null)
            return null;

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setUsername(document.getUserInfo().getUsername());
        documentDTO.setSubject(document.getSubject());
        documentDTO.setMessage(document.getMessage());

        String priority = Priority.getLabelByPriority(document.getPriority());
        documentDTO.setPriority(priority);
        return documentDTO;
    }

}
