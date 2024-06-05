package com.bhishma.ams.response.routine;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class TeacherSubjectResponse {
    List<RoutineSubject> subjectList;
    HttpStatus statusCode;
}
