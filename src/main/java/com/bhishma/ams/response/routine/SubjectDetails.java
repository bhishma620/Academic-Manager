package com.bhishma.ams.response.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectDetails {
    private String subCode;
    private String subName;
    private String sem;
    private String section;
}
