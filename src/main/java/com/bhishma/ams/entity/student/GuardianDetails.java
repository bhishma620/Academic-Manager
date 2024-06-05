package com.bhishma.ams.entity.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class GuardianDetails {
    @Id
    private String collegeId;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String email;
    private String presentAddress;
    private String presentCity;
    private String presentState;
    private String presentPin;
}
