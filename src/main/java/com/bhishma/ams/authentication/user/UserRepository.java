package com.bhishma.ams.authentication.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByMail(String mail);

    @Modifying
    @Transactional
    @Query(value = "UPDATE _user SET time = :time, otp = :otp WHERE mail = :mail", nativeQuery = true)
    void saveOtpAndTimeByMailId(@Param("mail") String mail, @Param("otp") String otp, @Param("time") String time);

    @Modifying
    @Transactional
    @Query(value = "UPDATE _user SET password = :password WHERE mail = :mail", nativeQuery = true)
    void updatePassword(@Param("mail") String mail, @Param("password") String password);
}
