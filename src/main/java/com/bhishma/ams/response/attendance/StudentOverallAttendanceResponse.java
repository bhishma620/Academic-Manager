package com.bhishma.ams.response.attendance;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentOverallAttendanceResponse implements Serializable {
    private String rollNO;
    private String collegeId;
    private String name;
    private String overallAttendance;
}
