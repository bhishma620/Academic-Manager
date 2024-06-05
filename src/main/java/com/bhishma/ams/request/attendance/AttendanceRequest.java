package com.bhishma.ams.request.attendance;

import lombok.Data;

import java.util.List;

@Data
public class AttendanceRequest {

    private String batch;
    private String section;
    private String subCode;
    private String date;
    private String classType;

    List<PresentStatusRequest> presentStatusRequests;

}
