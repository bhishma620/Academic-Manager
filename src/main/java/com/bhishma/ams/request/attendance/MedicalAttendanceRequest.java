package com.bhishma.ams.request.attendance;

import lombok.Data;

import java.util.List;

@Data
public class MedicalAttendanceRequest {
    private String collegeId;
    private String subCode;
    private List<MedicalAttendanceStatusRequest>attendanceRequests;
}
