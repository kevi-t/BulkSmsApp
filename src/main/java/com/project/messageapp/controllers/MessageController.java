package com.project.messageapp.controllers;

import com.project.messageapp.dtos.MessageDTO;
import com.project.messageapp.dtos.SmsRequest;
import com.project.messageapp.services.MessageService;
import com.project.messageapp.services.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/msgApp/sms")
public class MessageController {
    private final MessageService messageService;
    private final TwilioService twilioService;

    @Autowired
    public MessageController(MessageService messageService, TwilioService twilioService) {
        this.messageService = messageService;
        this.twilioService = twilioService;
    }

    @GetMapping("/search-file")
    public List<String> fetchFileData(@RequestParam String filePath) {
        return messageService.fetchFileData(filePath);
    }
    @PostMapping
    public void getUserSms(){

    }

    @PostMapping("/send-sms")
    public String sendSms(@RequestBody SmsRequest smsRequest) {
        twilioService.sendSms(smsRequest.getTo(), smsRequest.getMessage());
        return "SMS sent successfully!";
    }
    @PostMapping("/send-messages")
    public void sendMessages(@RequestBody MessageDTO request) {
        messageService.sendMessageToMultipleNumbers(request.getRecipientNumbers(), request.getMessage());
    }

}