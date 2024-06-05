package com.bhishma.ams.entity.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AddressInformation {
    @Id
    private String collegeId;
    private String presentAddress;
    private String presentCity;
    private String presentState;
    private String presentPin;

    private String permanentAddress;
    private String permanentCity;
    private String permanentState;
    private String permanentPin;
}
