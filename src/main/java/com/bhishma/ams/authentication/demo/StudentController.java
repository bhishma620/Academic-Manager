package com.bhishma.ams.authentication.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")

public class StudentController {

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> add(){
        return ResponseEntity.ok("CREATED");
    }

    @GetMapping("")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")

    public ResponseEntity<String> update(){
        return ResponseEntity.ok("Reading");
    }

}
