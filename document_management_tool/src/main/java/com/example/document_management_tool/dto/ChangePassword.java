package com.example.document_management_tool.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    @NotEmpty(message = "Old Password is mandatory")
    private String oldPassword;
    @NotEmpty(message = "New Password is mandatory")
    private String newPassword;
}
