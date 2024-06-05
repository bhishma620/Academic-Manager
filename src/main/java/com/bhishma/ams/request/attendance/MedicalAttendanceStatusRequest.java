package com.bhishma.ams.request.attendance;


import lombok.Data;



@Data
public class MedicalAttendanceStatusRequest {

    private String date;
    private boolean prevStatus;
    private boolean curStatus;
    private String classType;
}
