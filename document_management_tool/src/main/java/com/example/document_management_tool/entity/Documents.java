package com.example.document_management_tool.entity;

import com.example.document_management_tool.enums.Priority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Subject should not be empty")
    private String subject;

    @NotEmpty(message = "Message should not be empty")
    private String message;

    @Enumerated(EnumType.STRING)
    private Priority priority;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
}
