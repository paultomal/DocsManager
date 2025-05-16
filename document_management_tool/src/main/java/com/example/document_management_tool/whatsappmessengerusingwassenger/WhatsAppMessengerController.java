package com.example.document_management_tool.whatsappmessengerusingwassenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppMessengerController {

    @Autowired
    private WhatsAppMessengerService whatsAppMessengerService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody WhatsAppMessageRequest request) {
        return whatsAppMessengerService.sendMessage(request);
    }
}