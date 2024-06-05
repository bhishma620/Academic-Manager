package com.bhishma.ams.controller.subject;

import com.bhishma.ams.request.subject.SubjectRequest;
import com.bhishma.ams.request.subject.SubjectUpdateRequest;
import com.bhishma.ams.response.subject.SubjectResponse;
import com.bhishma.ams.service.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@CrossOrigin("http://127.0.0.1:5500")
public class SubjectDetailsController {

    @Autowired
    SubjectService subjectService;

    @PostMapping("/admin")
    ResponseEntity<String> addSubject(@RequestBody SubjectRequest subjectRequest) {

        return subjectService.addSubject(subjectRequest);
    }

    @GetMapping("/{batch}/{id}")
    ResponseEntity<SubjectResponse> getSubject(@PathVariable("batch") String batch,
                                                @PathVariable("id") String id) {
        return subjectService.getSubject(batch, id);
    }


    @PutMapping("/admin/{batch}/{id}")
    ResponseEntity<String> updateSubject(@PathVariable("batch") String batch,
                                         @PathVariable("id") String id
            , @RequestBody SubjectUpdateRequest subjectRes) {

        return subjectService.updateSubject(batch, id, subjectRes);

    }


    @DeleteMapping("/admin/{batch}/{id}")
    ResponseEntity<String> deleteSubject(@PathVariable("batch") String batch,
                                         @PathVariable("id") String id) {
        return subjectService.deleteSubject(batch, id);
    }


    @GetMapping("")
    ResponseEntity<List<SubjectResponse>> getAllSubjectByBatchAndSem(@RequestParam("sem") String sem,
                                                                     @RequestParam("batch") String batch) {
        return subjectService.getAllSubjectByBatchAndSem(batch, sem);
    }
}
