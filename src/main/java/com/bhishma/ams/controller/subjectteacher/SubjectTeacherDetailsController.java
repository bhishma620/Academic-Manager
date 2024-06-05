package com.bhishma.ams.controller.subjectteacher;


import com.bhishma.ams.request.subjectteacher.SubjectTeacherRequest;
import com.bhishma.ams.request.subjectteacher.SubjectTeacherUpdateRequest;
import com.bhishma.ams.response.subjectteacher.SubjectTeacherResponse;
import com.bhishma.ams.service.subjectteacher.SubjectTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject-teacher")
@CrossOrigin("http://127.0.0.1:5500")
public class SubjectTeacherDetailsController {

    @Autowired
    SubjectTeacherService subjectTeacherService;

    @PostMapping("")
    ResponseEntity<String> addSubjectTeacher(
            @RequestBody SubjectTeacherRequest subjectTeacherRequest){

       return subjectTeacherService.addSubjectTeacher(subjectTeacherRequest);
    }


    @GetMapping("")
    ResponseEntity<SubjectTeacherResponse> getSubjectTeacher(
            @RequestParam("batch") String batch,
            @RequestParam("subCode") String subjectCode,
            @RequestParam("section") String section
    ){

        return subjectTeacherService.getSubjectTeacher(batch,subjectCode,section);
    }

    @PutMapping ("/primary")

    ResponseEntity<String> updatePrimarySubjectTeacher(
            @RequestParam("batch") String batch,
            @RequestParam("subCode") String subjectCode,
            @RequestParam("section") String section,
            @RequestBody  SubjectTeacherUpdateRequest curSubjectTeacher
    ){

        return subjectTeacherService.updatePrimarySubjectTeacher(batch,subjectCode,section,curSubjectTeacher);
    }

    @PutMapping("/substitute")
    ResponseEntity<String> updateSubstituteSubjectTeacher(
            @RequestParam("batch") String batch,
            @RequestParam("subCode") String subjectCode,
            @RequestParam("section") String section,
            @RequestBody SubjectTeacherUpdateRequest curSubjectTeacher
    ){

        return subjectTeacherService.updateSubstituteSubjectTeacher(batch,subjectCode,section,curSubjectTeacher);
    }


    @DeleteMapping ("")
    ResponseEntity<String> deleteSubjectTeacher(
            @RequestParam("batch") String batch,
            @RequestParam("subCode") String subjectCode,
            @RequestParam("section") String section
    ){

        return subjectTeacherService.deleteSubjectTeacher(batch,subjectCode,section);
    }


}
