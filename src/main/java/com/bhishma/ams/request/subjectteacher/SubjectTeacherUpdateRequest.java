package com.bhishma.ams.request.subjectteacher;

import lombok.Data;

@Data
public class SubjectTeacherUpdateRequest {
    private String primaryTeacherId;
    private String substituteTeacherId;
    private String lastUpdateTime;
}
