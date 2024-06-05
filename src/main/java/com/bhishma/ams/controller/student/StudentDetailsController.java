package com.bhishma.ams.controller.student;


import com.bhishma.ams.request.student.StudentDetailsRequest;
import com.bhishma.ams.response.student.EducationalDetailsResponse;
import com.bhishma.ams.response.student.StudentResponse;
import com.bhishma.ams.response.student.StudentResponseForAttendence;
import com.bhishma.ams.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student")
@RestController
@CrossOrigin("http://127.0.0.1:5500")
public class StudentDetailsController {

    @Autowired
    StudentService studentService;

    @PostMapping("/admin")
    ResponseEntity<String> addStudent(
            @RequestBody StudentDetailsRequest studentDetailsRequest) {

        return studentService.addStudent(studentDetailsRequest);

    }

    //student profile by collegeID
    @GetMapping("/profile/collegeId")
    ResponseEntity<StudentResponse> getStudentProfileCollegeId(@RequestParam("collegeId") String id) {
            return studentService.getStudentProfileCollegeId(id);
    }

    //educational Details by roll
    @GetMapping("/admin/search/roll/{id}")
    ResponseEntity<StudentResponseForAttendence> getStudentByRoll(@PathVariable String id) {

        return studentService.getStudentByRoll(id);
    }

    //educational Details by collegeID
    @GetMapping("/admin/search/collegeId/{id}")
    ResponseEntity<StudentResponseForAttendence> getStudentByCollegeID(@PathVariable String id) {

        return studentService.getStudentByCollegeId(id);
    }

//    @PutMapping("/{id}")
//    ResponseEntity<String> updateStudent(@RequestBody StudentResponse studentResponse,
//                                         @PathVariable("id") String roll) {
//
//        return studentService.updateStudent(studentResponse, roll);
//
//
//    }

    @PutMapping("/admin/upgradeSem")
    ResponseEntity<String> upgradeSemester(@RequestParam("batch") String batch,
                                           @RequestParam("sem") String sem) {
        return studentService.upgradeSemesterByAdmin(batch, sem);

    }

    //
    @DeleteMapping("/admin/{id}")
    ResponseEntity<String> deleteStudent(
            @PathVariable("id") String roll) {

        return studentService.deleteStudent(roll);

    }

    @GetMapping("/admin")
    ResponseEntity<List<EducationalDetailsResponse>> getStudentsByBatchAndSection(@RequestParam("batch") String batch,
                                                                                  @RequestParam("section") String section) {
        return studentService.getStudentsByBatchAndRoll(batch, section);
    }

//    @GetMapping("/attendance")
//    ResponseEntity<List<StudentResponseForAttendence>> getStudentDetailsForAttendanceByBatchAndSection(
//            @RequestParam("batch") String batch,
//            @RequestParam("section") String section) {
//
//
//        return studentService.getAllStudentForAttendanceByBatchAndSection(batch,section);
//    }


    @GetMapping("/admin/all")
    ResponseEntity<List<EducationalDetailsResponse>> getAllStudentWithPaginationAndSort(
            @RequestParam(value = "pageNo") int offSet,
            @RequestParam(value = "pageSize") int pagesize,
            @RequestParam(value = "orderBy") String orderBy,
            @RequestParam(value = "sortBy") String order,
            @RequestParam(value = "batch") String batch,
            @RequestParam(value = "section") String section
    ) {

        return studentService.getAllStudentByBatchAndSemWithPaginationAndSort(offSet, pagesize, orderBy, order, batch, section);


    }


}
