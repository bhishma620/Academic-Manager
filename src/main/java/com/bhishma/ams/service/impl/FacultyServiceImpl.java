package com.bhishma.ams.service.impl;

import com.bhishma.ams.authentication.auth.AuthenticationService;
import com.bhishma.ams.authentication.auth.RegisterRequest;
import com.bhishma.ams.authentication.user.Role;
import com.bhishma.ams.entity.Faculty;
import com.bhishma.ams.exception.ResourceNotFoundException;
import com.bhishma.ams.repository.FacultyRepo;
import com.bhishma.ams.response.FacultyResponse;
import com.bhishma.ams.service.FacultyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    FacultyRepo facultyRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public ResponseEntity<String> addFaculty(Faculty faculty) {

       Optional<Faculty> facultyOptimal=facultyRepo.findById(faculty.getMailId());

       if(facultyOptimal.isPresent()){
           return new ResponseEntity<>("Already Present",HttpStatus.NOT_ACCEPTABLE);
       }

        RegisterRequest user = RegisterRequest.builder()
                .userId(faculty.getMailId())
                .password(faculty.getMailId())
                .mail(faculty.getMailId())
                .role(Role.TEACHER)
                .build();

        authenticationService.register(user);

        facultyRepo.save(faculty);


        return new ResponseEntity<>("Succesfully Added", HttpStatus.CREATED);

    }
    @Override
    public ResponseEntity<String> updateFaculty(FacultyResponse facultyResponse, String id) {
       Faculty faculty = facultyRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Faculty", "Id", id));

        facultyRepo.save(faculty);

        return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteFaculty(String id) {
        Faculty faculty = facultyRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Faculty", "Id", id));

        facultyRepo.delete(faculty);

        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);

    }



    @Override
    public ResponseEntity<FacultyResponse> getFaculty(String id) {
        Faculty faculty = facultyRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Faculty", "Id", id));


        FacultyResponse facultyResponse = modelMapper.map(faculty,FacultyResponse.class);

        return new ResponseEntity<>(facultyResponse, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<FacultyResponse>> getAllFaculty(){

        List<Faculty> faculties=facultyRepo.findAll();

        List<FacultyResponse> facultyResponses
                =faculties.stream()
                .map(faculty -> modelMapper.map(faculty, FacultyResponse.class)).toList();

        return new ResponseEntity<>(facultyResponses,HttpStatus.OK);
    }
}
