package com.bhishma.ams.request.routine;

import lombok.Data;

@Data
public class RoutineRequest {
    private String batch;

    private String sem;

    private String section;

    private String day;

    private String subCode;

    private String teacherId;

    private String startTime;

    private String endTime;

    private String classType;

    private String roomNo;

}
