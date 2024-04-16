package com.project.messageapp.services;


import com.project.messageapp.models.BulkSms;
import com.project.messageapp.models.SmsBatch;
import com.project.messageapp.repositories.BulkSmsRepository;
import com.project.messageapp.repositories.SmsUsersRepository;
import com.project.messageapp.responses.UniversalResponse;
import com.project.messageapp.utils.ReadFile;
import com.project.messageapp.utils.RequestContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final SmsUsersRepository smsUsersRepository;
    private  final ReadFile readFile;
    private final BulkSmsRepository bulkSmsRepository;

//    public UniversalResponse sendMessageToMultipleNumbers(String token, MessageDTO request) {
//        try {
//            Iterable<Users> users = usersRepository.findAll();
//            String senderAccount = RequestContext.getUsername();
//            Users senderUser = (Users) usersRepository.findUsersByUserEmail(senderAccount);
//
//            if (senderUser != null) {
//                Long senderUserId = senderUser.getUserId();
//                Batch batch = new Batch();
//                batch.setDateCreated(LocalDateTime.now());
//                batch.setMessageDescription(request.getMessageDescription());
//                batch.setUserId(senderUserId);
//                batch.setStatus("DRAFT");
//                batchRepository.save(batch);
//
//            for (Users user : users) {
//                String phoneNumber = user.getUsername();
//
//                Message msg = new Message();
//                msg.setSenderAccountNumber(senderAccount);
//                msg.setRecipientNumber(phoneNumber);
//                msg.setMsg(request.getMessage());
//                msg.setBatchId(batch.getBatchId()); // Set the associated batch for the message
//                msg.setCreatedAt(LocalDateTime.now());
//                msg.setStatus("DRAFT");
//                messageRepository.save(msg);
//
//
//            }
//            return UniversalResponse.builder()
//                    .message("Message sent successfully")
//                    .status("0")
//                    .build();
//            }
//            else {
//                return UniversalResponse.builder()
//                        .message("User Id not found")
//                        .status("1")
//                        .build();
//            }
//        }
//        catch (Exception ex) {
//            return UniversalResponse.builder()
//                    .message("Error while sending the message")
//                    .status("1")
//                    .build();
//        }
//    }
//
//    public UniversalResponse viewMessages(){
//        List<Message> msgList= messageRepository.findAllByStatusOrderByCreatedAtDesc("DRAFT");
//        if (msgList != null && !msgList.isEmpty()) {
//            List<FilteredList> filteredLists = new ArrayList<>();
//            for (Message message : msgList) {
//                FilteredList filteredList = new FilteredList();
//                filteredList.setMsg(message.getMsg());
//                filteredList.setRecipientNumber(message.getRecipientNumber());
//                filteredList.setSenderAccountNumber(message.getSenderAccountNumber());
//                filteredList.setCreatedAt(message.getCreatedAt());
//
//                filteredLists.add(filteredList);
//            }
//            return UniversalResponse.builder()
//                    .message("Request successful")
//                    .status("0")
//                    .data(filteredLists)
//                    .build();
//        }
//        else {
//            return UniversalResponse.builder()
//                    .message("No messages found")
//                    .status("1")
//                    .build();
//        }
//
//    }
//
//    public  UniversalResponse viewBatches(){
//        List<Batch> batchList = batchRepository.findAllByStatusOrderByDateCreatedDesc("DRAFT");
//        if (batchList != null && !batchList.isEmpty()) {
//            return UniversalResponse.builder()
//                    .message("Request successful")
//                    .status("0")
//                    .data(batchList)
//                    .build();
//        }
//        else {
//            return UniversalResponse.builder()
//                    .message("No batches found")
//                    .status("1")
//                    .build();
//        }
//    }

    public UniversalResponse sendFileMessage(String token, String msgText) {
        try {
            List<String> phoneNumbers = readFile.readPhoneNumbersFromExcel();
            if (phoneNumbers.isEmpty()) {
                return UniversalResponse.builder().message("No valid usernames found in the file").status("0").build();
            }
            String senderAccount = RequestContext.getUsername();
            var userId = smsUsersRepository.findSmsUsersByUserEmail(senderAccount).getUserId();

            for (String phoneNumber : phoneNumbers) {
                BulkSms bulkSms = new BulkSms();
                SmsBatch smsBatch = new SmsBatch();
                bulkSms.setUserId(userId);
                bulkSms.setMessages(msgText);
                bulkSms.setStatus("DRAFT");
                bulkSmsRepository.save(bulkSms);

            }
            return UniversalResponse.builder().message("Message sent successfully").status("0").build();
        }
        catch (Exception ex) {
            return UniversalResponse.builder().message("Error sending messages: " + ex.getMessage()).status("0").build();
        }
    }
}