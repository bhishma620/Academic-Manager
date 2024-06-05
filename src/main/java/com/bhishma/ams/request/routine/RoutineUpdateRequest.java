package com.bhishma.ams.request.routine;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class RoutineUpdateRequest {
    private String batch;

    private String sem;

    private String section;

    private String day;

    private String subCode;

    private String startTime;

    private String endTime;

    private String curStartTime;

    private String curEndTime;

    private String classType;

    private String roomNo;



    private String teacherId;

}
