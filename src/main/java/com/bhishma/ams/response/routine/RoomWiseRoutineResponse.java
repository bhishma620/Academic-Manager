package com.bhishma.ams.response.routine;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Data
public class RoomWiseRoutineResponse {
    List<SubjectReferenceResponse> subjectReferences;
    List<TeacherReferenceResponse>teacherReferences;
    Map<String, List<ClassResponseRoomNo>> weekDetails;
    HttpStatus statusCode;
}
