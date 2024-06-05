package com.bhishma.ams.service;

import com.bhishma.ams.request.login.PasswordResetRequest;
import org.springframework.http.ResponseEntity;

public interface PasswordService {

    ResponseEntity<String> sendOtp(String mail) ;

    ResponseEntity<String> verifyOtp(PasswordResetRequest passwordResetRequest);
}
