package com.project.messageapp.services;

import com.project.messageapp.dtos.MessageDTO;
import com.project.messageapp.models.KplcBulkSms;
import com.project.messageapp.models.KplcSmsBatch;
import com.project.messageapp.repositories.BulkSmsUsersRepository;
import com.project.messageapp.repositories.KplcBulkSmsRepository;
import com.project.messageapp.repositories.KplcSmsBatchRepository;
import com.project.messageapp.responses.UniversalResponse;
import com.project.messageapp.utils.ReadFile;
import com.project.messageapp.utils.RequestContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private final BulkSmsUsersRepository bulkSmsUsersRepository;
    private final KplcSmsBatchRepository kplcSmsBatchRepository;
    private final KplcBulkSmsRepository kplcBulkSmsRepository;
    private final ReadFile readFile;

    // Code to retrieve phone numbers fetched from a file and save to the database
    public UniversalResponse sendFileMessage(String token, MessageDTO request) {
        try {
            List<String> phoneNumbers = readFile.readPhoneNumbersFromExcel();

            // Fetch the username from the RequestContext
            String senderAccount = RequestContext.getUsername();

            // Log the senderAccount to debug the null issue
            log.info("Sender account: {}", senderAccount);
            // Create the batch once outside the loop
            KplcSmsBatch kplcSmsBatch = KplcSmsBatch.builder()
                    .batchDescription(request.getBatchDescription())
                    .staffNo(senderAccount)
                    .createDate(new Date())
                    .status("DRAFT")
                    .build();
            kplcSmsBatchRepository.save(kplcSmsBatch);

            for (String phoneNumber : phoneNumbers) {
                KplcBulkSms kplcBulkSms = KplcBulkSms.builder()
                        .batchId(kplcSmsBatch.getBatchId())
                        .messages(request.getMessage())
                        .phoneNumber(phoneNumber)
                        .createDate(new Date())
                        .status("DRAFT")
                        .build();
                kplcBulkSmsRepository.save(kplcBulkSms);
            }
            return UniversalResponse.builder().message("Messages sent successfully").status("1").build();
        }
        catch (Exception ex) {
            return UniversalResponse.builder().message("Error sending messages: " + ex.getMessage()).status("0").build();
        }
    }

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
}