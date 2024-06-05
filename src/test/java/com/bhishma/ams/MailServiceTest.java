package com.bhishma.ams;

import com.bhishma.ams.service.MailService;
import com.bhishma.ams.service.impl.MailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {
    @Autowired
    MailServiceImpl mailService;


    void otpTest(){
        mailService.sendOtp("baivabsarkar@gmail.com");
    }

}
