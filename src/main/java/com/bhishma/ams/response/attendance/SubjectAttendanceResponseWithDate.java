package com.bhishma.ams.response.attendance;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
public class SubjectAttendanceResponseWithDate implements Serializable {
    private String name;

    private String batch;
    private String sem;
    private String section;

    private String collegeId;
    private String rollNo;

    List<SubjectAttendanceStatusWithDate> statusByDateList;
    private HttpStatus statusCode;
}
