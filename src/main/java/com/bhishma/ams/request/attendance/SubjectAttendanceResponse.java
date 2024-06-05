package com.bhishma.ams.request.attendance;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectAttendanceResponse implements Serializable {
    private String subCode;
    private String subName;
    private int totalClass;
}
