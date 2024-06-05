package com.bhishma.ams.controller;

import com.bhishma.ams.request.login.MailRequest;
import com.bhishma.ams.request.login.PasswordResetRequest;
import com.bhishma.ams.service.MailService;
import com.bhishma.ams.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class TestController {
    @Autowired
    MailService mailService;

    @Autowired
    PasswordService passwordService;

    @GetMapping("")
    public String test() {
        return "HELLO FROM Teacher";
    }

    @PostMapping("/mail")
    public ResponseEntity<String> sendMail(@RequestBody MailRequest request) {
        return passwordService.sendOtp(request.getTo());
    }

    @PostMapping("/otp")
    public ResponseEntity<String>verifyOtp(@RequestBody PasswordResetRequest passwordResetRequest){
        return passwordService.verifyOtp(passwordResetRequest);
    }
}
