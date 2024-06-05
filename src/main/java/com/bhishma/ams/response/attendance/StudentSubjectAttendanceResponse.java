package com.bhishma.ams.response.attendance;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentSubjectAttendanceResponse implements Serializable {
    private String subName;
    private String subCode;
    private int totalClass;
    private int presentClass;
    private String percentage;
}
