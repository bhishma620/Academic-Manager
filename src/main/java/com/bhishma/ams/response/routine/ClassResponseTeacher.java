package com.bhishma.ams.response.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassResponseTeacher {
    private String subCode;
    private String startTime;
    private String endTime;
    private String roomNo;
    private String section;
    private String classType;
    private String day;
}
