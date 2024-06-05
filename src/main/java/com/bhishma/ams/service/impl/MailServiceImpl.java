package com.bhishma.ams.service.impl;

import com.bhishma.ams.service.MailService;

import freemarker.template.Template;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;


    private boolean sendEmail(String sendTo, Map<String, Object> model) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            // Load template
            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            // Setup email parameters
            helper.setTo(sendTo);
            helper.setText(html, true);
            helper.setSubject("OTP for resetting your password");
            helper.setFrom("contact.ams.system@gmail.com");

            // Send email
            javaMailSender.send(message);


        } catch (Exception e) {
            return false;
        }

        return true;
    }


    @Override
    public ResponseEntity<String> sendOtp(String to) {

        String otp = getOtp(6);
        Map<String, Object> model = new HashMap<>();
        model.put("otp", otp);
        boolean status = sendEmail(to, model);

        if (status) return ResponseEntity.ok(otp);

        return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getOtp(int lengthOfOtp) {
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < lengthOfOtp; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return otp.toString();
    }

}
