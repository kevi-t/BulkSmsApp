//package com.project.messageapp.twilio;
//
//import com.project.messageapp.configurations.TwilioConfiguration;
//import com.project.messageapp.repositories.MessageRepository;
//import com.project.messageapp.responses.UniversalResponse;
//import com.project.messageapp.utils.Encryption;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TwilioService {
//    private final MessageRepository messageRepository;
//    private final TwilioConfiguration twilioConfig;
//    private  Encryption encryption;
//
//    @Autowired
//    public TwilioService(MessageRepository messageRepository, TwilioConfiguration twilioConfig) {
//        this.messageRepository = messageRepository;
//        this.twilioConfig = twilioConfig;
//    }
//
//    public UniversalResponse sendSms(SmsRequest smsRequest) {
//        try {
//            Message.creator(new PhoneNumber("+"+smsRequest.getReceiverPhoneNumber()),
//                    new PhoneNumber(twilioConfig.getTwilioTrialNumber()),
//                    String.valueOf(smsRequest.getMessage())).create();
//            saveSmsToDatabase(smsRequest.getReceiverPhoneNumber(), smsRequest.getMessage());
//            return UniversalResponse.builder()
//                    .message("Message sent successfully")
//                    .status("0")
//                    .build();
//        }
//        catch (Exception ex) {
//            System.out.println("Error while sending message"+ex);
//            return UniversalResponse.builder()
//                    .message("Error while sending the message")
//                    .status("1")
//                    .build();
//        }
//    }
//    private void saveSmsToDatabase(String phoneNumber, String message) {
//        com.project.messageapp.models.Message msg = new com.project.messageapp.models.Message();
//        //String encryptedMessage = encryption.encrypt(message);
//        msg.setMsg(message);
//        msg.setRecipientNumber(phoneNumber);
//        msg.setStatus("SENT");
//        msg.setSenderAccountNumber("0792526394");
//        messageRepository.save(msg);
//    }
//}