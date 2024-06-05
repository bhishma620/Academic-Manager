package com.bhishma.ams.request.routine;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class UpdateSubstituteRequest {
    private String subCode;
    private String batch;
    private String sem;
    private String section;
    private String subTeacherId;
    private String classType;
    private String day;
}
