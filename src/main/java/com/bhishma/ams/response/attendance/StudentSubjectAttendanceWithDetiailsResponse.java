package com.bhishma.ams.response.attendance;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
public class StudentSubjectAttendanceWithDetiailsResponse implements Serializable {
    private String firstName;
    private String lastName;
    private String rollNo;
    private String collegeId;
    private String batch;
    private String sem;
    private String section;

    List<StudentSubjectAttendanceResponse> studentSubjectAttendanceResponseList;

    private HttpStatus statusCode;
}
