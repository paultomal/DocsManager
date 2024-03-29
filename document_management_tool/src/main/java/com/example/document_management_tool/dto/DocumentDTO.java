package com.example.document_management_tool.dto;

import com.example.document_management_tool.entity.Documents;
import com.example.document_management_tool.enums.Priority;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long userId;

    public static DocumentDTO form(Documents documents) {
        if (documents == null)
            return null;

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(documents.getId());
        documentDTO.setUsername(documents.getUserInfo().getUsername());
        documentDTO.setSubject(documents.getSubject());
        documentDTO.setMessage(documents.getMessage());
        documentDTO.setUserId(documents.getUserInfo().getId());

        String priority = Priority.getLabelByPriority(documents.getPriority());
        documentDTO.setPriority(priority);
        return documentDTO;
    }

}
