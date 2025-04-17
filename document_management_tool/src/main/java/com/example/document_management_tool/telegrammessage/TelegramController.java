package com.example.document_management_tool.telegrammessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/telegram")
public class TelegramController {

    @Autowired
    private TelegramService telegramService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody TelegramMessageRequest request) {
        return telegramService.sendMessage(request.getChatId(), request.getMessage());
    }
}
