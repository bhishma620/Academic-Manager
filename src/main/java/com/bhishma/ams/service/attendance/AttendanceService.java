package com.bhishma.ams.service.attendance;


import com.bhishma.ams.request.attendance.AttendanceRequest;
import com.bhishma.ams.request.attendance.MedicalAttendanceRequest;
import com.bhishma.ams.request.attendance.PresentStatusRequest;
import com.bhishma.ams.request.attendance.SubjectAttendanceResponse;
import com.bhishma.ams.response.attendance.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttendanceService {

    ResponseEntity<String> addAttendance(AttendanceRequest attendanceRequest);

   StudentsAttendanceResponse getStudentsByBatchAndSection(String batch, String section,
                                                                            String subCode, String date,String classType);

    ResponseEntity<List<StudentSubjectAttendanceResponse>> getSubjectAttendanceByBatchCollegeIdAndSemSection(
            String batch, String collegeId, String sem, String section);

     StudentSubjectAttendanceWithDetiailsResponse getSubjectAttendanceByRollNo(String rollNo);

   StudentSubjectAttendanceWithDetiailsResponse
    getSubjectAttendanceByCollegeId(String collegeId);
    SemWiseSubjectAttendance getSubjectAttendanceByBatchSectionSem(
            String batch,String section,String sem
    );

    public StudentAttendanceReportResponse getStudentsOverallAttendanceByBatchSectionSem(
            String batch, String section, String sem
    );

    SubjectAttendanceResponseWithDate getSubjectAttendanceResponseWithDateByRollNoOrCollegeId(String id, String subCode,String sem);

    ResponseEntity<String> updateMedicalAttendance(MedicalAttendanceRequest medicalAttendanceRequest);
}
