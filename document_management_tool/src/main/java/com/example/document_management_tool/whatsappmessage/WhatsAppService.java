package com.example.document_management_tool.whatsappmessage;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    private final TwilioConfig twilioConfig;

    @Autowired
    public WhatsAppService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public String sendMessage(String to, String messageBody) {
        try {
            Message.creator(
                    new PhoneNumber("whatsapp:" + to),
                    new PhoneNumber(twilioConfig.getFromNumber()),
                    messageBody
            ).create();

            return "Message sent to: " + to;
        } catch (Exception e) {
            return "Failed to send message: " + e.getMessage();
        }
    }
}
