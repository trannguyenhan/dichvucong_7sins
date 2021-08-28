package com.example.dichvucong_7sin.Controller;

import com.google.api.gax.core.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SpeechToTextController {
    private CredentialsProvider credentialsProvider;

    @Autowired
    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }



}
