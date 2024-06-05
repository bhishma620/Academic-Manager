package com.bhishma.ams.entity.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EducationalDetails {
    @Id
    private String collegeId;
    private String firstName;
    private String lastName;
    private String batch;
    private String department;
    private String rollNo;
    private String regNo;
    private String regular;
    private String sem;
    private String section;
}
