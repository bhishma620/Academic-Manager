package com.bhishma.ams.service.subject;

import com.bhishma.ams.request.subject.SubjectRequest;
import com.bhishma.ams.request.subject.SubjectUpdateRequest;
import com.bhishma.ams.response.attendance.StudentSubjectAttendanceResponse;
import com.bhishma.ams.response.subject.SubjectResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubjectService {


    ResponseEntity<String> addSubject(SubjectRequest subjectRequest);
    ResponseEntity<SubjectResponse> getSubject(String batch, String id);
    ResponseEntity<String> updateSubject(String batch,String id, SubjectUpdateRequest subjectRes);
    ResponseEntity<String> deleteSubject(String batch,String id);
    ResponseEntity<List<SubjectResponse>> getAllSubjectByBatchAndSem(String batch, String sem);

}
