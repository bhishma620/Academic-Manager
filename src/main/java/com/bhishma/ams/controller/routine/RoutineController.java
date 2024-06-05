package com.bhishma.ams.controller.routine;

import com.bhishma.ams.request.routine.RoutineRequest;
import com.bhishma.ams.request.routine.RoutineUpdateRequest;
import com.bhishma.ams.request.routine.UpdateSubstituteRequest;
import com.bhishma.ams.response.routine.*;
import com.bhishma.ams.service.impl.routine.RoutineServiceImpl;
import com.bhishma.ams.service.routine.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routine")
@CrossOrigin("http://127.0.0.1:5500")
public class RoutineController {

    @Autowired
    RoutineService routineService;

    @PostMapping("")
    ResponseEntity<String> addRoutine(@RequestBody RoutineRequest routineRequest) {
        return routineService.addRoutine(routineRequest);
    }

    @PutMapping("")
    ResponseEntity<String>addRoutine(@RequestBody RoutineUpdateRequest updateRequest){
        return routineService.updateRoutine(updateRequest);
    }

    @GetMapping("search/section")
    SectionWiseRoutineResponse getSectionWiseRoutine(@RequestParam("section") String section,
                                                     @RequestParam("batch") String batch) {

        return routineService.getSectionWiseRoutine(batch, section);
    }

    @GetMapping("search/teacherId")
    TeacherWiseRoutineResponse getTeacherWiseRoutine(@RequestParam("teacherId") String teacherId) {

        return routineService.getTeacherWiseRoutine(teacherId);

    }

    @GetMapping("search/roomNo")
    RoomWiseRoutineResponse getRoomWiseRoutine(@RequestParam("roomNo") String roomNo) {
        return routineService.getRoomWiseRoutine(roomNo);
    }

    @GetMapping("subject/own")
    TeacherSubjectResponse getTeacherWiseOwnSubjectByDay(@RequestParam("day") String day,
                                                      @RequestParam("teacherId") String teacherId) {
        return routineService.getTeacherWiseOwnSubjectByDay(day, teacherId);
    }

    @GetMapping("subject/all")
    TeacherSubjectResponse getTeacherWiseAllSubjectByDay(@RequestParam("day") String day,
                                                      @RequestParam("teacherId") String teacherId) {
        return routineService.getTeacherWiseAllSubjectByDay(day, teacherId);
    }

    @GetMapping("subject/all/teacherId")
    TeacherWeekWiseSubjectResponse getAllSubjectByTeacherId(@RequestParam("teacherId")String teacherId){
        return routineService.getAllSubjectByTeacherId(teacherId);
    }

    @PutMapping("/substitute")
    ResponseEntity<String> addSubstituteTeacher(@RequestBody UpdateSubstituteRequest request) {

        return routineService.addSubstituteTeacher(request.getBatch(), request.getSem(), request.getSection(),
                request.getSubCode(),request.getClassType(), request.getDay(), request.getSubTeacherId());
    }



}
