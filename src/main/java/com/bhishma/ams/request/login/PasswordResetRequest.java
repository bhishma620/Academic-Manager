package com.bhishma.ams.request.login;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String mailId;
    private String otp;
    private String newPassword;
}
