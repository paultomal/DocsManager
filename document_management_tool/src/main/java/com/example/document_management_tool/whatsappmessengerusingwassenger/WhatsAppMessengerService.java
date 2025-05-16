package com.example.document_management_tool.whatsappmessengerusingwassenger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WhatsAppMessengerService {

    private static final String API_URL = "https://api.wassenger.com/v1/messages";
    @Value("${wassenger.api.key}")
    private String apiKey;

    public String sendMessage(WhatsAppMessageRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", apiKey);

        String body = String.format("{\"phone\":\"%s\",\"message\":\"%s\"}", request.getPhone(), request.getMessage());

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }
}
