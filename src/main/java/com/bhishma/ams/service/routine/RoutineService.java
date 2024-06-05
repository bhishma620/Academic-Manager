package com.bhishma.ams.service.routine;

import com.bhishma.ams.request.routine.RoutineRequest;
import com.bhishma.ams.request.routine.RoutineUpdateRequest;
import com.bhishma.ams.response.routine.*;
import org.springframework.http.ResponseEntity;

public interface RoutineService {
    ResponseEntity<String> addRoutine(RoutineRequest routineRequest);

    SectionWiseRoutineResponse getSectionWiseRoutine(String batch, String section);

    TeacherWiseRoutineResponse getTeacherWiseRoutine(String teacherId);

    RoomWiseRoutineResponse getRoomWiseRoutine(String roomNo);

   TeacherSubjectResponse getTeacherWiseOwnSubjectByDay(String day, String teacherId);

    ResponseEntity<String> addSubstituteTeacher(String batch, String sem, String section, String subCode,
                                                String classType, String day,String subTeacherId);

    TeacherSubjectResponse getTeacherWiseAllSubjectByDay(String day, String teacherId);

    TeacherWeekWiseSubjectResponse getAllSubjectByTeacherId(String teacherId);

    ResponseEntity<String> updateRoutine(RoutineUpdateRequest updateRequest);
}
