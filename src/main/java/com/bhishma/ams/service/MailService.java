package com.bhishma.ams.service;


import org.springframework.http.ResponseEntity;

public interface MailService {




    ResponseEntity<String> sendOtp(String to);

}
