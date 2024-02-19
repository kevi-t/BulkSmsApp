package com.project.messageapp.controllers;

import com.project.messageapp.dtos.MessageDTO;
import com.project.messageapp.dtos.MsgDTO;
import com.project.messageapp.responses.UniversalResponse;
import com.project.messageapp.services.MessageService;
import com.project.messageapp.twilio.SmsRequest;
import com.project.messageapp.twilio.TwilioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/msgApp/")
public class MessageController {
    private final MessageService messageService;
    private final TwilioService twilioService;

    @Autowired
    public MessageController(MessageService messageService, TwilioService twilioService) {
        this.messageService = messageService;
        this.twilioService = twilioService;
    }

    @PostMapping("/sendSms")
    public ResponseEntity<UniversalResponse> sendSms(@RequestBody @Valid SmsRequest request) {
        try {
            return ResponseEntity.ok(twilioService.sendSms(request));
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/composeMessage")
    public ResponseEntity<UniversalResponse> sendMessages(@RequestBody MessageDTO request, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok( messageService.sendMessageToMultipleNumbers(token,request.getRecipientNumbers(), request.getMessage()));
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sendFileMessage")
    public ResponseEntity<UniversalResponse> sendFileMessages(@RequestBody MsgDTO request, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok( messageService.sendFileMessage(token,request.getMessage()));
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/viewMessages")
    public ResponseEntity<UniversalResponse> viewMessages() {
        try {
            return ResponseEntity.ok(messageService.viewMessages());
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}