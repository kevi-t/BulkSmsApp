package com.project.messageapp.services;

import com.project.messageapp.dtos.FilteredList;
import com.project.messageapp.jwt.JwtTokenService;
import com.project.messageapp.models.Message;
import com.project.messageapp.models.Users;
import com.project.messageapp.repositories.MessageRepository;
import com.project.messageapp.repositories.UsersRepository;
import com.project.messageapp.responses.UniversalResponse;
import com.project.messageapp.utils.ReadFile;
import com.project.messageapp.utils.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UsersRepository usersRepository;
    private  final ReadFile readFile;

    @Autowired
    public MessageService(MessageRepository messageRepository, UsersRepository usersRepository, JwtTokenService jwtTokenService, ReadFile readFile) {
        this.messageRepository = messageRepository;
        this.usersRepository = usersRepository;
        this.readFile = readFile;
    }

    public UniversalResponse sendMessageToMultipleNumbers(String token, String[] phoneNumbers, String messageText) {
        try {
            Iterable<Users> users = usersRepository.findAll();
            String senderAccount = RequestContext.getUsername();
            for (Users user : users) {
                String phoneNumber = user.getPhoneNumber();

                Message msg = new Message();
                msg.setSenderAccountNumber(senderAccount);
                msg.setRecipientNumber(phoneNumber);
                msg.setMsg(messageText);
                msg.setStatus("SENT");
                //Save message to the database
                messageRepository.save(msg);
            }
            return UniversalResponse.builder()
                    .message("Message sent successfully")
                    .status("0")
                    .build();
        }
        catch (Exception ex) {
            return UniversalResponse.builder()
                    .message("Error while sending the message")
                    .status("1")
                    .build();
        }

    }

    public UniversalResponse viewMessages(){
        List<Message> msgList= messageRepository.findAllByStatusOrderByCreatedAtDesc("SENT");
        if (msgList != null && !msgList.isEmpty()) {
            List<FilteredList> filteredLists = new ArrayList<>();
            for (Message message : msgList) {
                FilteredList filteredList = new FilteredList();
                filteredList.setMsg(message.getMsg());
                filteredList.setRecipientNumber(message.getRecipientNumber());
                filteredList.setSenderAccountNumber(message.getSenderAccountNumber());
                filteredList.setCreatedAt(message.getCreatedAt());

                filteredLists.add(filteredList);
            }
            return UniversalResponse.builder()
                    .message("Request successful")
                    .status("0")
                    .data(filteredLists)
                    .build();
        }
        else {
            return UniversalResponse.builder()
                    .message("No messages found")
                    .status("1")
                    .build();
        }

    }

    public UniversalResponse sendFileMessage(String token, String msgText) {
        try {
            List<String> phoneNumbers = readFile.readPhoneNumbersFromExcel();
            if (phoneNumbers.isEmpty()) {
                return UniversalResponse.builder()
                        .message("No valid usernames found in the file")
                        .status("1")
                        .build();
            }
            String senderAccount = RequestContext.getUsername();

            for (String phoneNumber : phoneNumbers) {
                Message msg = new Message();
                msg.setSenderAccountNumber(senderAccount);
                msg.setRecipientNumber(phoneNumber);
                msg.setMsg(msgText);
                msg.setStatus("SENT");

                // Save message to the database
                messageRepository.save(msg);
            }

            return UniversalResponse.builder()
                    .message("Message sent successfully")
                    .status("0")
                    .build();
        }
        catch (Exception ex) {
            return UniversalResponse.builder()
                    .message("Error sending messages: " + ex.getMessage())
                    .status("1")
                    .build();
        }
    }

}