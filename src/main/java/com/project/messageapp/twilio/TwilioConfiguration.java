package com.project.messageapp.twilio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class TwilioConfiguration {

    private String twilioAccountSid;
    private String twilioTrialNumber;
    private String twilioAuthToken;

    public TwilioConfiguration(){

    }

}