package com.project.messageapp.controllers;

import com.project.messageapp.dtos.MessageDTO;
import com.project.messageapp.responses.UniversalResponse;
import com.project.messageapp.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/msgApp/")
@Validated
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;
   // private final CustomMessageService customMessageService;

//    @Autowired
//    public MessageController(MessageService messageService, TwilioService twilioService, CustomMessageService customMessageService) {
//        this.messageService = messageService;
//        this.twilioService = twilioService;
//        this.customMessageService = customMessageService;
//    }

//    @PostMapping("/sendSms")
//    public ResponseEntity<UniversalResponse> sendSms(@RequestBody @Valid SmsRequest request) {
//        try {
//            return ResponseEntity.ok(twilioService.sendSms(request));
//        }
//        catch (Exception exception){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/composeMessage")
//    public ResponseEntity<UniversalResponse> sendMessages(@RequestBody @Valid MessageDTO request, @RequestHeader("Authorization") String token) {
//        try {
//            return ResponseEntity.ok( messageService.sendMessageToMultipleNumbers(token,request));
//        }
//        catch (Exception exception){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/sendFileMessage")
    public ResponseEntity<UniversalResponse> sendFileMessages(@RequestBody MessageDTO request, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok( messageService.sendFileMessage(token,request.getMessage()));
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    @PostMapping("/customMessage")
//    public ResponseEntity<UniversalResponse> sendCustomMessageToUser(@RequestHeader("Authorization") String token) {
//        try {
//            return ResponseEntity.ok(customMessageService.sendCustomMessageToUsers(token));
//        }
//        catch (Exception exception){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//    @GetMapping("/viewMessages")
//    public ResponseEntity<UniversalResponse> viewMessages() {
//        try {
//            return ResponseEntity.ok(messageService.viewMessages());
//        }
//        catch (Exception exception){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/viewBatches")
//    public ResponseEntity<UniversalResponse> viewBatches() {
//        try {
//            return ResponseEntity.ok(messageService.viewBatches());
//        }
//        catch (Exception exception){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

}