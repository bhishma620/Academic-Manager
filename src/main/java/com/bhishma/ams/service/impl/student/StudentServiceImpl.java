package com.bhishma.ams.service.impl.student;

import com.bhishma.ams.authentication.auth.AuthenticationService;
import com.bhishma.ams.authentication.auth.RegisterRequest;
import com.bhishma.ams.authentication.user.Role;
import com.bhishma.ams.entity.CurrentSem;
import com.bhishma.ams.entity.student.CurrentContact;
import com.bhishma.ams.entity.student.EducationalDetails;
import com.bhishma.ams.entity.student.SemesterMarks;
import com.bhishma.ams.entity.student.Student;
import com.bhishma.ams.exception.ResourceNotFoundException;
import com.bhishma.ams.repository.CurrentSemRepo;
import com.bhishma.ams.repository.student.*;
import com.bhishma.ams.request.student.CurrentContactUpdateRequest;
import com.bhishma.ams.request.student.SemesterMarksUpdateRequest;
import com.bhishma.ams.request.student.StudentDetailsRequest;
import com.bhishma.ams.response.student.EducationalDetailsResponse;
import com.bhishma.ams.response.student.StudentResponse;
import com.bhishma.ams.response.student.StudentResponseForAttendence;
import com.bhishma.ams.service.student.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;


    @Autowired
    private EducationalRepo educationalRepo;
    @Autowired
    private PersonalInformationRepo personalInformationRepo;

    @Autowired
    private CurrentContactRepo currentContactRepo;

    @Autowired
    AddressInformationRepo addressInformationRepo;

    @Autowired
    private CurrentCoOrdinateRepo currentCoOrdinateRepo;

    @Autowired
    private GuardianDetailsRepo guardianDetailsRepo;

    @Autowired
    private MarksRepo marksRepo;

    @Autowired
    private SemesterMarksRepo semesterMarksRepo;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CurrentSemRepo currentSemRepo;

    //add student from admin
    @Override
    public ResponseEntity<String> addStudent(StudentDetailsRequest studentDetailsRequest) {



        try {

            Optional<EducationalDetails> studentOptional = educationalRepo.findByCollegeId(studentDetailsRequest.getCollegeId());

            if (studentOptional.isPresent()) {
                return new ResponseEntity<>("Student Already Exist", HttpStatus.CONFLICT);
            } else {

                Student student = modelMapper.map(studentDetailsRequest, Student.class);
                //save the student id as userId and password first time
                RegisterRequest user = RegisterRequest.builder()
                        .userId(student.getCollegeId())
                        .password(student.getCollegeId())
                        .mail(student.getPersonalInformation().getEmail())
                        .role(Role.STUDENT)
                        .build();

                authenticationService.register(user);

                //save student information
                save(student);

                return new ResponseEntity<>("Successfully Added", HttpStatus.CREATED);
            }
        } catch (Exception e) {

            return new ResponseEntity<>("Failed to save data: ", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private void save(Student student) {

        educationalRepo.save(student.getEducationalDetails());
        personalInformationRepo.save(student.getPersonalInformation());
        currentContactRepo.save(student.getCurrentContact());
        addressInformationRepo.save(student.getAddressInformation());
        currentCoOrdinateRepo.save(student.getCurrentCoOrdinate());
        guardianDetailsRepo.save(student.getGuardianDetails());
        marksRepo.save(student.getMarks());
        semesterMarksRepo.save(student.getSemesterMarks());
        studentRepo.save(student);

    }


    @Override
    public ResponseEntity<StudentResponse> getStudentProfileCollegeId(String collegeId) {

        Student student = studentRepo.findByCollegeId(collegeId).
                orElseThrow(() -> new ResourceNotFoundException("Student", "College Id", collegeId));

        StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StudentResponseForAttendence> getStudentByRoll(String roll) {

        EducationalDetails student = educationalRepo.findByRollNo(roll).
                orElseThrow(() -> new ResourceNotFoundException("Student", "Roll No", roll));

        StudentResponseForAttendence studentResponse = modelMapper.map(student, StudentResponseForAttendence.class);

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StudentResponseForAttendence> getStudentByCollegeId(String id) {
        EducationalDetails student = educationalRepo.findByCollegeId(id).
                orElseThrow(() -> new ResourceNotFoundException("Student", "College Id", id));

        StudentResponseForAttendence studentResponse = modelMapper.map(student, StudentResponseForAttendence.class);

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    //semester update by admin

    @Override
    public ResponseEntity<String> upgradeSemesterByAdmin(String batch, String sem) {

        try {

            String curSem = currentSemRepo.findSemByBatch(batch);


            if (curSem == null || (Integer.parseInt(curSem) + 1 == Integer.parseInt(sem))) {
                educationalRepo.upgradeSemester(batch, sem);
                if(curSem==null) currentSemRepo.save(new CurrentSem(batch, sem));
                else currentSemRepo.upgrade(batch,sem);
                return new ResponseEntity<>("Semester Upgraded!!", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Wrongly Choosen", HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Failed To Saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @Override
    public ResponseEntity<String> deleteStudent(String collegeId) {
        Student student = studentRepo.findById(collegeId).
                orElseThrow(() -> new ResourceNotFoundException("Student", "College Id", collegeId));


        studentRepo.deleteById(collegeId);

        return new ResponseEntity<>("Deleted Succesfully", HttpStatus.OK);
    }

    //
//
//    @Override
//    public ResponseEntity<String> setPassword(String newPassword, String mail) {
//
//
//        Student student=studentRepo.findByMail(mail);
//
//        student.setPassword(newPassword);
//
//        studentRepo.save(student);
//
//        return new ResponseEntity<>("Password updated Successfully",
//                HttpStatus.OK);
//
//    }
//
    @Override
    public ResponseEntity<List<EducationalDetailsResponse>> getAllStudentByBatchAndSemWithPaginationAndSort(int offSet,
                                                                                                            int pagesize,
                                                                                                            String orderBy,
                                                                                                            String order,
                                                                                                            String batch,
                                                                                                            String section) {

        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(offSet, pagesize, Sort.by(orderBy).ascending());
        } else {
            pageable = PageRequest.of(offSet, pagesize, Sort.by(orderBy).descending());
        }

        Page<EducationalDetails> responsePage = educationalRepo.findAllByBatchAndSection(batch, section, pageable);

        List<EducationalDetailsResponse> studentResponses = responsePage.getContent().stream().map(student -> {
            return modelMapper.map(student, EducationalDetailsResponse.class);
        }).toList();


        return new ResponseEntity<>(studentResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EducationalDetailsResponse>> getStudentsByBatchAndRoll(String batch, String section) {

        List<EducationalDetails> students = educationalRepo.findAllByBatchAndSection(batch, section);

        List<EducationalDetailsResponse> studentResponses = students.stream().map(student -> modelMapper.map(student, EducationalDetailsResponse.class)).toList();

        return new ResponseEntity<>(studentResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateStudentCurrentContact(String collegeId,
                                                              CurrentContactUpdateRequest currentContactUpdateRequest) {

        Optional<CurrentContact> currentContact = currentContactRepo.findById(collegeId);

        if (currentContact.isPresent()) {
            CurrentContact currentContact1 = currentContact.get();

            currentContact1.setEmail(currentContactUpdateRequest.getEmail());
            currentContact1.setMobileNo(currentContactUpdateRequest.getMobileNo());


            currentContactRepo.save(currentContact1);
        } else {
            CurrentContact currentContact1 = modelMapper.map(currentContactUpdateRequest, CurrentContact.class);
            currentContactRepo.save(currentContact1);
        }

        return new ResponseEntity<>("Updated Succesfully", HttpStatus.OK);

    }

    public ResponseEntity<String> updateSemesterMarksByStudent(String collegeId,
                                                               SemesterMarksUpdateRequest semesterMarksUpdateRequest) {
        Optional<SemesterMarks> semesterMarks = semesterMarksRepo.findById(collegeId);

        if (semesterMarks.isPresent()) {
            SemesterMarks curSemMarks = semesterMarks.get();

            curSemMarks.setSem1(semesterMarksUpdateRequest.getSem1());
            curSemMarks.setSem2(semesterMarksUpdateRequest.getSem2());
            curSemMarks.setSem3(semesterMarksUpdateRequest.getSem3());
            curSemMarks.setSem4(semesterMarksUpdateRequest.getSem4());
            curSemMarks.setSem5(semesterMarksUpdateRequest.getSem5());
            curSemMarks.setSem6(semesterMarksUpdateRequest.getSem6());
            curSemMarks.setSem7(semesterMarksUpdateRequest.getSem7());
            curSemMarks.setSem8(semesterMarksUpdateRequest.getSem8());
            semesterMarksRepo.save(curSemMarks);
        } else {
            SemesterMarks semesterMarks1 = modelMapper.map(semesterMarksUpdateRequest, SemesterMarks.class);
            semesterMarksRepo.save(semesterMarks1);
        }

        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
    }

}

