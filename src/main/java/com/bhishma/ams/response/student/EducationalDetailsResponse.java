package com.bhishma.ams.response.student;

import lombok.Data;

@Data
public class EducationalDetailsResponse {
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
