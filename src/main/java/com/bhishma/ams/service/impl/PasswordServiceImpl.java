package com.bhishma.ams.service.impl;

import com.bhishma.ams.authentication.user.User;
import com.bhishma.ams.authentication.user.UserRepository;
import com.bhishma.ams.request.login.PasswordResetRequest;
import com.bhishma.ams.service.MailService;
import com.bhishma.ams.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${ams-authentication.otp-validation-time}")
    private long otpValidationTime;

    @Override
    public ResponseEntity<String> sendOtp(String mail) {

        Optional<User> user=userRepository.findByMail(mail);

        if(user.isPresent()) {
            ResponseEntity<String>response=mailService.sendOtp(mail);

            if(response.getStatusCode()==HttpStatus.OK){

                LocalDateTime currentDateTime1 = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Format the current date and time (optional)
                String formattedDateTime = currentDateTime1.format(formatter);

                userRepository.saveOtpAndTimeByMailId(mail,response.getBody(),formattedDateTime);


                return new ResponseEntity<>("Successfully Send",HttpStatus.OK);

            }
            else{
                return new ResponseEntity<>("Failed",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> verifyOtp(PasswordResetRequest passwordResetRequest){

        Optional<User> user=userRepository.findByMail(passwordResetRequest.getMailId());

        if(user.isPresent()){
            User curUser=user.get();

            String otp=curUser.getOtp();
            String time=curUser.getTime();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime time2=LocalDateTime.parse(time, formatter);

            LocalDateTime time1 = LocalDateTime.now();

            Duration duration = Duration.between(time1, time2);

            long seconds = Math.abs(duration.getSeconds());


            if(seconds<=otpValidationTime){
               if(otp.equals(passwordResetRequest.getOtp())){

                   String password=passwordEncoder.encode(passwordResetRequest.getNewPassword());

                   userRepository.updatePassword(passwordResetRequest.getMailId(),password);

                   return new ResponseEntity<>("PassWord Updated",HttpStatus.OK);
               }
               else return new ResponseEntity<>("Otp is not Matched",HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>("Time Over,Try to Send Another",HttpStatus.NOT_ACCEPTABLE);
            }

        }

        else{
            return new ResponseEntity<>("User Not Valid",HttpStatus.BAD_REQUEST);
        }
    }

}
