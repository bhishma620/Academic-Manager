package com.bhishma.ams.controller.attendance;


import com.bhishma.ams.request.attendance.AttendanceRequest;
import com.bhishma.ams.request.attendance.MedicalAttendanceRequest;
import com.bhishma.ams.request.attendance.SubjectAttendanceResponse;
import com.bhishma.ams.response.attendance.*;
import com.bhishma.ams.service.attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin("http://127.0.0.1:5500")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;

    @PostMapping("")
    ResponseEntity<String> addAttendance(@RequestBody AttendanceRequest attendanceRequest) {

        return attendanceService.addAttendance(attendanceRequest);

    }

    @GetMapping("")
    @Cacheable(cacheNames = "StudentsAttendanceResponse", keyGenerator = "cachingKey")
    public StudentsAttendanceResponse getStudentsByBatchAndSection(@RequestParam("batch") String batch,
                                                                   @RequestParam("section") String section,
                                                                   @RequestParam("subCode") String subCode,
                                                                   @RequestParam("date") String date,
                                                                   @RequestParam("classType") String clasType) {
        return attendanceService.getStudentsByBatchAndSection(batch, section, subCode, date, clasType);
    }

    @GetMapping("/admin/rollNo/{rollNo}")
    @Cacheable(cacheNames = "StudentSubjectAttendanceWithDetiailsResponse", keyGenerator = "cachingKey")
    public StudentSubjectAttendanceWithDetiailsResponse getStudentSubjectAttendanceByRollNo(
            @PathVariable("rollNo") String rollNo) {

        return attendanceService.getSubjectAttendanceByRollNo(rollNo);

    }


    @GetMapping("/Id")
    @Cacheable(cacheNames = "StudentSubjectAttendanceWithDetiailsResponse", keyGenerator = "cachingKey")
    public StudentSubjectAttendanceWithDetiailsResponse getStudentSubjectAttendanceByCollegeId(
            @RequestParam("collegeId") String collegeId) {
        return attendanceService.getSubjectAttendanceByCollegeId(collegeId);
    }

    @GetMapping("/admin/subject")
    @Cacheable(cacheNames = "SemWiseSubjectAttendance", keyGenerator = "cachingKey")
    public SemWiseSubjectAttendance getSubjectAttendanceByBatchSectionSem(
            @RequestParam("batch") String batch, @RequestParam("section") String section,
            @RequestParam("sem") String sem
    ) {
        return attendanceService.getSubjectAttendanceByBatchSectionSem(batch, section, sem);
    }

    @GetMapping("/admin/class")
    @Cacheable(cacheNames = "StudentAttendanceReportResponse", keyGenerator = "cachingKey")
    public StudentAttendanceReportResponse getClassWiseStudentAttendanceByBatchSectionSem(
            @RequestParam("batch") String batch, @RequestParam("section") String section,
            @RequestParam("sem") String sem
    ) {

        return attendanceService.getStudentsOverallAttendanceByBatchSectionSem(batch, section, sem);
    }

    @GetMapping("/subject")
    @Cacheable(cacheNames = "SubjectAttendanceResponseWithDate", keyGenerator = "cachingKey")
    public SubjectAttendanceResponseWithDate getSubjectAttendanceResponseWithDateByRollNoOrCollegeId(
            @RequestParam("id") String id,
            @RequestParam("subCode") String subCode,
            @RequestParam("sem") String sem
    ) {
        return attendanceService.getSubjectAttendanceResponseWithDateByRollNoOrCollegeId(id, subCode,sem);
    }

    @PostMapping("/subject/medical")
    public ResponseEntity<String> updateDateWiseStudentAttendance(@RequestBody MedicalAttendanceRequest medicalAttendanceRequest) {
        return attendanceService.updateMedicalAttendance(medicalAttendanceRequest);
    }


}
