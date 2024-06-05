package com.bhishma.ams.response.subjectteacher;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class SubjectTeacherResponse {

    private String batch;

    private  String subCode;

    private String section;

    private String primaryTeacherId;

    private  String substituteTeacherId;

    private String lastUpdateTime;

}
