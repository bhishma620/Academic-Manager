package com.bhishma.ams.response.attendance;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
public class StudentAttendanceReportResponse implements Serializable {
    private List<StudentOverallAttendanceResponse>studentSubjectAttendanceResponses;
    private HttpStatus httpStatus;
}
