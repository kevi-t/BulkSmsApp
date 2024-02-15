package com.project.messageapp.utils;

import com.project.messageapp.responses.UniversalResponse;

public class SessionAlert {
    public UniversalResponse getSessionErrorMsg(){
        return UniversalResponse.builder().status("1").message("Session time Expired").build();
    }
}