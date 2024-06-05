package com.bhishma.ams.response.routine;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class TeacherWeekWiseSubjectResponse {

    List<SubjectDetails> subjects;
    HttpStatus statusCode;
}
