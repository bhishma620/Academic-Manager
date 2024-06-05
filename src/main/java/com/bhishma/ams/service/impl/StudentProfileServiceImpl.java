//package com.bhishma.ams.service.impl;
//
//import com.bhishma.ams.response.*;
//import com.bhishma.ams.service.attendance.AttendanceService;
//import com.bhishma.ams.service.StudentProfileService;
//import com.bhishma.ams.service.subject.SubjectService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class StudentProfileServiceImpl implements StudentProfileService {
//
//    @Autowired
//    SubjectService subjectService;
//
//    @Autowired
//    AttendanceService attendanceService;
//
//    @Autowired
//    ModelMapper modelMapper;
//
//@Override
//public ResponseEntity<List<SubjectAttendenceResponse>> getStudentProfile(String batch, String roll, String sem) {
//
//
//    List<SubjectAttendenceResponse> subjectAttendenceResponses=new ArrayList<>();
//
//
//       ResponseEntity< List<StudentSubjectResponse>> subjects
//                =subjectService.getAllSubjectByBatchAndSem( batch, sem);
//
//       for(StudentSubjectResponse subjectResponse:subjects.getBody()){
//
//           StudentSubjectAttendence studentSubjectAttendence =
//                   attendanceService.getSujectAttendanceByRoll(roll,subjectResponse.getId()).getBody();
//
//           double percentage=(1.0*studentSubjectAttendence.getPresent()/
//                   studentSubjectAttendence.getTotal())*100;
//
//           SubjectAttendenceResponse subjectAttendenceResponse =
//                   modelMapper.map(studentSubjectAttendence,SubjectAttendenceResponse.class);
//
//                   subjectAttendenceResponse.setSubjectName(subjectResponse.getName());
//                   subjectAttendenceResponse.setSubjectCode(subjectResponse.getId());
//                   subjectAttendenceResponse.setPersentage(String.format("%.2f",percentage));
//
//                   subjectAttendenceResponses.add(subjectAttendenceResponse);
//       }
//
//return new ResponseEntity<>(subjectAttendenceResponses, HttpStatus.OK);
//    }
//
//}
