package com.bhishma.ams.response.routine;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Data
public class TeacherWiseRoutineResponse {

    List<SubjectReferenceResponse>subjectReferenceResponses;
    Map<String, List<ClassResponseTeacher>> weekDetails;
    HttpStatus statusCode;

}
