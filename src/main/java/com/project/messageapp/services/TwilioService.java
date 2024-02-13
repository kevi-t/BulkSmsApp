package com.project.messageapp.services;

import com.project.messageapp.repositories.MessageRepository;
import com.project.messageapp.utils.Encryption;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    private final MessageRepository messageRepository;
    private  Encryption encryption;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @Autowired
    public TwilioService( MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendSms(String to, String message) {
        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();

        // Save the SMS to the database
        saveSmsToDatabase(to, message);

    }
    private void saveSmsToDatabase(String phoneNumber, String message) {
        com.project.messageapp.models.Message msg = new com.project.messageapp.models.Message();
        String encryptedMessage = encryption.encrypt(message);
        msg.setRecipientNumber(phoneNumber);
        msg.setMsg(encryptedMessage);
        messageRepository.save(msg);
    }
}