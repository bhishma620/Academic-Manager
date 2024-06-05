package com.bhishma.ams.controller;

import com.bhishma.ams.authentication.auth.AuthenticationRequest;
import com.bhishma.ams.authentication.auth.AuthenticationResponse;
import com.bhishma.ams.authentication.auth.AuthenticationService;
import com.bhishma.ams.request.login.MailRequest;
import com.bhishma.ams.request.login.PasswordResetRequest;
import com.bhishma.ams.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://127.0.0.1:5500")
public class LoginController {

    @Autowired
    AuthenticationService service;

    @Autowired
    PasswordService passwordService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<String>verifyOtp(@RequestBody PasswordResetRequest passwordResetRequest){
        return passwordService.verifyOtp(passwordResetRequest);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendMail(@RequestBody MailRequest request) {
        return passwordService.sendOtp(request.getTo());
    }

}
