package com.bhishma.ams.config.subjectteacher;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectTeacherKey {
    private String batch;
    private String section;
    private  String subCode;
}
