package com.example.document_management_tool.telegrammessage;

import lombok.Data;

@Data
public class TelegramMessageRequest {
    private String chatId;
    private String message;
}
