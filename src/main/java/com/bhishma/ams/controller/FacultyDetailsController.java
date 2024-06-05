package com.bhishma.ams.controller;

import com.bhishma.ams.entity.Faculty;
import com.bhishma.ams.response.FacultyResponse;
import com.bhishma.ams.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/faculty")
@CrossOrigin("http://127.0.0.1:5500")
@RestController

public class FacultyDetailsController {

    @Autowired
    FacultyService facultyService;

    @PostMapping("")
    ResponseEntity<String> addFaculty(
            @RequestBody Faculty faculty  ){

        return facultyService.addFaculty(faculty);

    }


    @GetMapping("/{id}")
    ResponseEntity<FacultyResponse> getFaculty(
            @PathVariable("id") String id){

        return facultyService.getFaculty(id);

    }



    @PutMapping("/{id}")

    ResponseEntity<String> updateFaculty(@RequestBody FacultyResponse FacultyResponse,
                                         @PathVariable("id") String id){

        return facultyService.updateFaculty(FacultyResponse,id);


    }

    @DeleteMapping("/{id}")

    ResponseEntity<String> deleteFaculty(
            @PathVariable("id") String id){

        return facultyService.deleteFaculty(id);

    }

    @GetMapping("/all")
ResponseEntity<List<FacultyResponse>>getAllFaculty(){
        return facultyService.getAllFaculty();
    }
    
}
