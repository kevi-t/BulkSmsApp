package com.project.messageapp.twilio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SmsRequest {
    private  String receiverPhoneNumber;
    private  String message;

    @Override
    public String toString() {
        return "SmsRequest{" +"phoneNumber='" + receiverPhoneNumber + '\'' +", message=" + message +'}';
    }
}