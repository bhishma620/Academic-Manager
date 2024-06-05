package com.bhishma.ams.response.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoutineSubject {
    private String subCode;
    private String subName;
    private String classType;
    private String batch;
    private String sem;
    private String section;
    private String accessType;
}
