package com.bhishma.ams.controller.studentprofile;

import com.bhishma.ams.repository.student.StudentRepo;
import com.bhishma.ams.request.student.CurrentContactUpdateRequest;
import com.bhishma.ams.request.student.SemesterMarksUpdateRequest;
import com.bhishma.ams.response.attendance.StudentSubjectAttendanceResponse;
import com.bhishma.ams.response.student.StudentResponse;
import com.bhishma.ams.service.attendance.AttendanceService;
import com.bhishma.ams.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-profile")
@CrossOrigin("http://127.0.0.1:5500")
public class StudentProfileController
{

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    StudentService studentService;
    @GetMapping("/profile")
    ResponseEntity<StudentResponse>getStudentProfile(@RequestParam("collegeId") String collegeId){
        return studentService.getStudentProfileCollegeId(collegeId);
    }

    @GetMapping("/attendance")
    ResponseEntity<List<StudentSubjectAttendanceResponse>> getStudentSubjectAttendance(
            @RequestParam("batch")String batch,@RequestParam("sem")String sem,
            @RequestParam("section")String section,@RequestParam("collegeId") String collegeId){
        return attendanceService.getSubjectAttendanceByBatchCollegeIdAndSemSection(batch,collegeId,sem,section);
    }

    @PutMapping("/update/currentAddress")
    ResponseEntity<String>updateCurrentAddress(@RequestParam("collegeId")String collegeId,
                                               @RequestBody CurrentContactUpdateRequest currentContactUpdateRequest){
        return studentService.updateStudentCurrentContact(collegeId,currentContactUpdateRequest);
    }

    @PutMapping("/update/semesterMarks")
    ResponseEntity<String>updateSemesterMarksByStudent(@RequestParam("collegeId") String collegeId,
                                               @RequestBody SemesterMarksUpdateRequest semesterMarksUpdateRequest){
        return studentService.updateSemesterMarksByStudent(collegeId,semesterMarksUpdateRequest);
    }



}
