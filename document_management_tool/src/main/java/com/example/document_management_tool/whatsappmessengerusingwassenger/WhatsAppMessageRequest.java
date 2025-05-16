package com.example.document_management_tool.whatsappmessengerusingwassenger;


import lombok.Data;

@Data
public class WhatsAppMessageRequest {
    private String phone;
    private String message;
}