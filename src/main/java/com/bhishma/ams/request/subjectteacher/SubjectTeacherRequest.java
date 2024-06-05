package com.bhishma.ams.request.subjectteacher;


import lombok.Data;

@Data
public class SubjectTeacherRequest {

    private String batch;

    private  String subCode;

    private String section;

    private String primaryTeacherId;

}
