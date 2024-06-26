package com.project.messageapp.services;//package com.project.messageapp.services;
//
//import com.project.messageapp.dtos.UsersDTO;
//import com.project.messageapp.models.Message;
//import com.project.messageapp.repositories.MessageRepository;
//import com.project.messageapp.repositories.UsersRepository;
//import com.project.messageapp.responses.UniversalResponse;
//import com.project.messageapp.utils.MessageConstants;
//import com.project.messageapp.utils.RequestContext;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class CustomMessageService {
//    private final MessageRepository messageRepository;
//    private final UsersRepository usersRepository;
//
//    @Autowired
//    public CustomMessageService(MessageRepository messageRepository, UsersRepository usersRepository) {
//        this.messageRepository = messageRepository;
//        this.usersRepository = usersRepository;
//    }
//
//    public UniversalResponse sendCustomMessageToUsers(String token) {
//      try {
//
//          List<UsersDTO> usersData = usersRepository.findUsersDTOBy();
//          System.out.println(usersData);
//
//          String senderAccount = RequestContext.getUsername();
//          for (UsersDTO users : usersData) {
//              String phone = users.getPhoneNumber();
//              String name = users.getFullName();
//              String account = users.getAccountNumber();
//
//              String messageText = String.format(MessageConstants.RECHARGE_MESSAGE_TEMPLATE, name, account);
//              System.out.println(messageText);
//              Message msg = new Message();
//              msg.setSenderAccountNumber(senderAccount);
//              msg.setRecipientNumber(phone);
//              msg.setMsg(messageText);
//              msg.setStatus("SENT");
//              messageRepository.save(msg);
//          }
//          return UniversalResponse.builder()
//                  .message("Message sent successfully")
//                  .status("0")
//                  .build();
//
//      }
//      catch (Exception exception){
//          log.error("Error while sending the message", exception);
//          return UniversalResponse.builder()
//                  .message("Error while sending the message")
//                  .status("1")
//                  .build();
//      }
//
//    }
//
//    private void sendMessage(String phoneNumber, String message) {
//        // Implement the logic to send messages (e.g., using a messaging service or SMS gateway)
//        System.out.println("Sending message to " + phoneNumber + ": " + message);
//    }
//}