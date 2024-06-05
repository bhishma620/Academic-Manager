package com.bhishma.ams.service;

import com.bhishma.ams.entity.Faculty;
import com.bhishma.ams.response.FacultyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FacultyService {

    ResponseEntity<String> updateFaculty(FacultyResponse facultyResponse, String id);

    ResponseEntity<String> deleteFaculty(String id);
    ResponseEntity<String> addFaculty(Faculty faculty);

    ResponseEntity<FacultyResponse> getFaculty(String  id);

    ResponseEntity<List<FacultyResponse>> getAllFaculty();
}
