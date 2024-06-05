package com.bhishma.ams.service.student;

import com.bhishma.ams.request.student.CurrentContactUpdateRequest;
import com.bhishma.ams.request.student.SemesterMarksUpdateRequest;
import com.bhishma.ams.request.student.StudentDetailsRequest;
import com.bhishma.ams.response.student.EducationalDetailsResponse;
import com.bhishma.ams.response.student.StudentResponse;
import com.bhishma.ams.response.student.StudentResponseForAttendence;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {

    ResponseEntity<String> addStudent(StudentDetailsRequest studentDetailsRequest);

    ResponseEntity<StudentResponse> getStudentProfileCollegeId(String collegeId);

    ResponseEntity<StudentResponseForAttendence> getStudentByRoll(String roll);

    ResponseEntity<StudentResponseForAttendence> getStudentByCollegeId(String id);

    ResponseEntity<String> upgradeSemesterByAdmin(String batch, String sem);

    //    ResponseEntity<String> updateStudent(StudentResponse studentResponse, String roll);
//
    ResponseEntity<String> deleteStudent(String collegeId);

    //
//    ResponseEntity<String> setPassword(String newPassword, String mail);
//
    ResponseEntity<List<EducationalDetailsResponse>> getAllStudentByBatchAndSemWithPaginationAndSort(int offSet, int pagesize,
                                                                                          String orderBy,
                                                                                          String order,
                                                                                          String batch,
                                                                                          String section);
    ResponseEntity<List<EducationalDetailsResponse>> getStudentsByBatchAndRoll(String batch, String section);

   ResponseEntity<String>updateStudentCurrentContact(String collegeId,
                                                             CurrentContactUpdateRequest currentContactUpdateRequest);

    ResponseEntity<String>updateSemesterMarksByStudent(String collegeId,
                                                       SemesterMarksUpdateRequest semesterMarksUpdateRequest);
}
