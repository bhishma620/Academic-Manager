package com.bhishma.ams.request.subject;

import lombok.Data;

@Data
public class SubjectRequest {
    private String subCode;
    private String subName;
    private String sem;
    private String batch;
}
