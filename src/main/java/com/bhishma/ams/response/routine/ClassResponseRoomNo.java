package com.bhishma.ams.response.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassResponseRoomNo {
    private String subCode;
    private String teacherId;
    private String startTime;
    private String endTime;
    private String section;
    private String classType;
    private String day;
}
