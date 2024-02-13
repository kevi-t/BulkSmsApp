package com.project.messageapp.services;

import com.project.messageapp.models.Customers;
import com.project.messageapp.models.Message;
import com.project.messageapp.repositories.CustomersRepository;
import com.project.messageapp.repositories.MessageRepository;
import com.project.messageapp.utils.ReadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final CustomersRepository customersRepository;
    private  final ReadFile readFile;

    @Autowired
    public MessageService(MessageRepository messageRepository, CustomersRepository customersRepository, ReadFile readFile) {
        this.messageRepository = messageRepository;
        this.customersRepository = customersRepository;
        this.readFile = readFile;
    }

    public void sendMessageToMultipleNumbers(String[] phoneNumbers, String messageText) {
        Iterable<Customers> customers = customersRepository.findAll();

        for (Customers customers1 : customers) {
            String phoneNumber = customers1.getPhoneNumber();

            Message msg = new Message();
            msg.setRecipientNumber(phoneNumber);
            msg.setMsg(messageText);
            msg.setStatus("SENT");
            //Save message to the database
            messageRepository.save(msg);
        }
    }
    public List<String> fetchFileData(String filePath) {
        return readFile.readUsernamesFromExcel(filePath);
    }
}