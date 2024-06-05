package com.bhishma.ams.service.impl.attendance;

import com.bhishma.ams.entity.attendance.Attendance;
import com.bhishma.ams.entity.attendance.AttendanceStatusOnDate;
import com.bhishma.ams.entity.attendance.StudentRecord;
import com.bhishma.ams.entity.attendance.SubjectRecord;
import com.bhishma.ams.entity.student.EducationalDetails;
import com.bhishma.ams.entity.subject.Subject;
import com.bhishma.ams.exception.ResourceNotFoundException;
import com.bhishma.ams.repository.attendance.AttendanceRepo;
import com.bhishma.ams.repository.attendance.AttendanceStatusOnDateRepo;
import com.bhishma.ams.repository.attendance.StudentRecordRepo;
import com.bhishma.ams.repository.attendance.SubjectRecordRepo;
import com.bhishma.ams.repository.student.EducationalRepo;
import com.bhishma.ams.repository.subject.SubjectRepo;
import com.bhishma.ams.request.attendance.AttendanceRequest;
import com.bhishma.ams.request.attendance.MedicalAttendanceRequest;
import com.bhishma.ams.request.attendance.PresentStatusRequest;
import com.bhishma.ams.request.attendance.SubjectAttendanceResponse;
import com.bhishma.ams.response.attendance.*;
import com.bhishma.ams.service.attendance.AttendanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    AttendanceRepo attendanceRepo;

    @Autowired
    StudentRecordRepo studentRecordRepo;

    @Autowired
    SubjectRecordRepo subjectRecordRepo;

    @Autowired
    AttendanceStatusOnDateRepo attendanceStatusOnDateRepo;

    @Autowired
    EducationalRepo educationalRepo;

    @Autowired
    SubjectRepo subjectRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> addAttendance(AttendanceRequest attendanceRequests) {

        String batch = attendanceRequests.getBatch();
        String section = attendanceRequests.getSection();
        String date = attendanceRequests.getDate();
        String subCode = attendanceRequests.getSubCode();
        String classType = attendanceRequests.getClassType();

        List<PresentStatusRequest> studentsAttendanceStatuses = attendanceRequests.getPresentStatusRequests();

        Optional<AttendanceStatusOnDate> attendanceStatusOnDate =
                attendanceStatusOnDateRepo.findByBatchAndSectionAndSubCodeAndDateAndClassType(batch,
                        section, subCode, date, classType);

        if (!attendanceStatusOnDate.isPresent()) {

            //save Today date as attendance done
            AttendanceStatusOnDate attendanceStatusOnDate1 = new AttendanceStatusOnDate(batch, section, subCode, date, classType);
            attendanceStatusOnDateRepo.save(attendanceStatusOnDate1);

            //updating total class count
            updateSubjectClassCount(batch, section, subCode);

            for (PresentStatusRequest student : studentsAttendanceStatuses) {
                //saving attendance of student
                saveStudentAttendance(student, date, subCode, classType);

                //updating total present status of student
                updateStudentTotalPresent(student, subCode);


            }

            return new ResponseEntity<>("Attendance Updated", HttpStatus.OK);
        }


        //Already exists need to update
        updateAttendance(studentsAttendanceStatuses, batch, section, subCode, date, classType);

        return new ResponseEntity<>("Attendance Re-updated", HttpStatus.OK);
    }

    private void updateAttendance(List<PresentStatusRequest> studentsAttendanceStatuses, String batch, String section,
                                  String subCode, String date, String classType) {

        studentsAttendanceStatuses.forEach(student -> {

            //getting college id
            String collegeId = student.getCollegeId();

            // Get the attendance status
            Boolean status = attendanceRepo.findAttendanceStatus(collegeId, subCode, date, classType).orElse(false);

            // Get the current status of the student
            boolean curStatus = student.isStatus();


            // Update student status
            student.setStatus(curStatus);

            // Save current status
            saveStudentAttendance(student, date, subCode, classType);

            // Update student attendance count
            studentRecordRepo.findByCollegeIdAndSubCode(collegeId, subCode)
                    .ifPresent(subjectRecord -> {
                        int curClass = subjectRecord.getTotalClass();


                        if (!status && curStatus) {
                            subjectRecord.setTotalClass(curClass + 1);

                        } else if (status && !curStatus) {
                            subjectRecord.setTotalClass(curClass - 1);
                        }


                        studentRecordRepo.save(subjectRecord);
                    });
        });


    }

    private void updateSubjectClassCount(String batch, String section, String subCode) {
        Optional<SubjectRecord> subjectRecord = subjectRecordRepo.findByBatchAndSectionAndSubCode(batch, section, subCode);

        if (subjectRecord.isPresent()) {
            SubjectRecord subjectRecord1 = subjectRecord.get();
            subjectRecord1.setTotalClass(subjectRecord1.getTotalClass() + 1);
            subjectRecordRepo.save(subjectRecord1);
        } else {
            SubjectRecord subjectRecord1 = new SubjectRecord(batch, subCode, section, 1);
            subjectRecordRepo.save(subjectRecord1);
        }
    }

    private void updateStudentTotalPresent(PresentStatusRequest student, String subCode) {
        Optional<StudentRecord> studentRecord = studentRecordRepo.findByCollegeIdAndSubCode(
                student.getCollegeId(), subCode);
        if (studentRecord.isPresent()) {
            StudentRecord studentRecord1 = studentRecord.get();
            int cur = studentRecord1.getTotalClass();
            studentRecord1.setTotalClass(cur + (student.isStatus() ? 1 : 0));
            studentRecordRepo.save(studentRecord1);
        } else {
            StudentRecord studentRecord1 = new StudentRecord(student.getCollegeId(), subCode, (student.isStatus() ? 1 : 0));
            studentRecordRepo.save(studentRecord1);
        }
    }

    private void saveStudentAttendance(PresentStatusRequest student, String date, String subCode, String classType) {
        Attendance attendance = modelMapper.map(student, Attendance.class);
        attendance.setDate(date);
        attendance.setSubCode(subCode);
        attendance.setClassType(classType);
        attendanceRepo.save(attendance);
    }


    @Override
    public StudentsAttendanceResponse getStudentsByBatchAndSection(String batch, String section, String subCode, String date, String classType) {

/*
        List<EducationalDetails> educationalDetailsList = educationalRepo.findAllByBatchAndSection(batch, section);

        int totalSubClass = subjectRecordRepo.getTotalClass(batch, section, subCode).orElse(0);

        List<PresentStatusResponse> presentStatusResponseList = educationalDetailsList.stream()
                .map(student -> {

                    PresentStatusResponse presentStatusResponse = modelMapper.map(student, PresentStatusResponse.class);

                    Boolean status = attendanceRepo.findAttendanceStatus(student.getCollegeId(), subCode, date, classType).orElse(false); // Set the status not present initially

                    int totalPresentClass = studentRecordRepo.getTotalClass(student.getCollegeId(), subCode).orElse(0);

                    double percentage = 0.00;

                    if (totalSubClass > 0) {
                        percentage = ((double) totalPresentClass / totalSubClass) * 100;
                    }

                    String subPercentage = String.format("%.2f", percentage);

                    presentStatusResponse.setTotalPresentClass(totalPresentClass);
                    presentStatusResponse.setPercentage(subPercentage);
                    presentStatusResponse.setStatus(status);

                    return presentStatusResponse;
                })
                .toList();


        StudentsAttendanceResponse studentsAttendanceResponse = new StudentsAttendanceResponse();

        studentsAttendanceResponse.setPresentStatusResponseList(presentStatusResponseList);

        studentsAttendanceResponse.setTotalClass(totalSubClass);

        studentsAttendanceResponse.setStatusCode(HttpStatus.OK);

        return studentsAttendanceResponse;

 */
        StudentsAttendanceResponse response=new StudentsAttendanceResponse();

        int totalSubClass = subjectRecordRepo.getTotalClass(batch, section, subCode).orElse(0);

        if(attendanceStatusOnDateRepo.findByBatchAndSectionAndSubCodeAndDateAndClassType(batch,
                section, subCode, date, classType).isPresent()){

            List<PresentStatusResponse>presentStatusResponses=
                    attendanceRepo.getAttendanceStatusTotalClassByBatchSectionSubCodeDateClassType(batch,section,subCode,date,classType);

            List<PresentStatusResponse>presentStatusResponsesWithPercentages=
                    presentStatusResponses.stream().map(student->{
                        double percentage = ((double) student.getTotalPresentClass()/ totalSubClass) * 100;
                        student.setPercentage(String.format("%.2f", percentage));
                        return student;
                    }).toList();

            response.setPresentStatusResponseList(presentStatusResponsesWithPercentages);
        }
        else{

            if(totalSubClass!=0) {
                //for current date attendance
                List<PresentStatusResponse> presentStatusResponses =
                        attendanceRepo.getStudentWithTotalClassByBatchSectionSubCodeDateClassType(subCode);

                List<PresentStatusResponse>presentStatusResponsesWithPercentages=
                        presentStatusResponses.stream().map(student->{
                            double percentage = ((double) student.getTotalPresentClass()/ totalSubClass) * 100;
                            student.setPercentage(String.format("%.2f", percentage));
                            return student;
                        }).toList();
                response.setPresentStatusResponseList(presentStatusResponsesWithPercentages);

            }
            else{
                //if first class of that subject
                List<PresentStatusResponse>presentStatusResponses=
                        attendanceRepo.getStudentForFirstClassByBatchAndSection(batch,section);
                response.setPresentStatusResponseList(presentStatusResponses);
            }

        }
        response.setTotalClass(totalSubClass);
        response.setStatusCode(HttpStatus.OK);

        return response;

    }

    //get subjectWise attendance By collegeId and Sem ,section,batch

    @Override
    public ResponseEntity<List<StudentSubjectAttendanceResponse>>
    getSubjectAttendanceByBatchCollegeIdAndSemSection(String batch, String collegeId,
                                                      String sem, String section) {

        List<StudentSubjectAttendanceResponse> responseList =
                getStudentSubjectAttendanceResponses(batch, collegeId, sem, section);

        return new ResponseEntity<>(responseList, HttpStatus.OK);

    }

    private List<StudentSubjectAttendanceResponse> getStudentSubjectAttendanceResponses(String batch, String collegeId, String sem, String section) {
        List<Subject> subjectResponses = subjectRepo.findAllByBatchAndSem(batch, sem);


        List<StudentSubjectAttendanceResponse> responseList =
                subjectResponses.stream().map(subject -> {


                            Optional<Integer> totalClass =
                                    subjectRecordRepo.getTotalClass(batch, section, subject.getSubCode());

                            int subjectTotalClass = (totalClass.orElse(0));

                            Optional<Integer> totalClassOfStudent = studentRecordRepo.getTotalClass(collegeId, subject.getSubCode());

                            int studentTotalClass = (totalClassOfStudent.orElse(0));


                            StudentSubjectAttendanceResponse studentSubjectAttendanceResponse = modelMapper.
                                    map(subject, StudentSubjectAttendanceResponse.class);

                            //calculating percentage
                            double percentage = 0.00;

                            if (subjectTotalClass > 0) {
                                percentage = ((double) studentTotalClass / subjectTotalClass) * 100;
                            }

                            String subPercentage = String.format("%.2f", percentage);

                            studentSubjectAttendanceResponse.setTotalClass(subjectTotalClass);
                            studentSubjectAttendanceResponse.setPresentClass(studentTotalClass);

                            studentSubjectAttendanceResponse.setPercentage(subPercentage);

                            return studentSubjectAttendanceResponse;
                        }
                ).toList();
        return responseList;
    }


    @Override
    public StudentSubjectAttendanceWithDetiailsResponse
    getSubjectAttendanceByRollNo(String rollNo) {


        EducationalDetails educationalDetails = educationalRepo.findByRollNo(rollNo).
                orElseThrow(() -> new ResourceNotFoundException("Student", "Roll No: ", rollNo));

        StudentSubjectAttendanceWithDetiailsResponse studentSubjectAttendanceWithDetiailsResponse =
                modelMapper.map(educationalDetails, StudentSubjectAttendanceWithDetiailsResponse.class);

        String sem = educationalDetails.getSem();
        String batch = educationalDetails.getBatch();
        String section = educationalDetails.getSection();
        String collegeId = educationalDetails.getCollegeId();


        List<StudentSubjectAttendanceResponse> responseList =
                getStudentSubjectAttendanceResponses(batch, collegeId, sem, section);

        studentSubjectAttendanceWithDetiailsResponse.setStudentSubjectAttendanceResponseList(responseList);

        studentSubjectAttendanceWithDetiailsResponse.setStatusCode(HttpStatus.OK);

        return studentSubjectAttendanceWithDetiailsResponse;
    }

    @Override
    public StudentSubjectAttendanceWithDetiailsResponse getSubjectAttendanceByCollegeId(String id) {

        String collegeId=id;

        Optional<EducationalDetails> isRollNo = educationalRepo.findByRollNo(id);


        if(isRollNo.isPresent()){
            collegeId=isRollNo.get().getCollegeId();
        }

        EducationalDetails educationalDetails = educationalRepo.findById(collegeId).
                orElseThrow(() -> new ResourceNotFoundException("Student", "collegeId: ", id));

        StudentSubjectAttendanceWithDetiailsResponse studentSubjectAttendanceWithDetiailsResponse =
                modelMapper.map(educationalDetails, StudentSubjectAttendanceWithDetiailsResponse.class);

        String sem = educationalDetails.getSem();
        String batch = educationalDetails.getBatch();
        String section = educationalDetails.getSection();


        List<StudentSubjectAttendanceResponse> responseList =
                getStudentSubjectAttendanceResponses(batch, collegeId, sem, section);

        studentSubjectAttendanceWithDetiailsResponse.setStudentSubjectAttendanceResponseList(responseList);

        studentSubjectAttendanceWithDetiailsResponse.setStatusCode(HttpStatus.OK);

        return studentSubjectAttendanceWithDetiailsResponse;
    }


    public SemWiseSubjectAttendance getSubjectAttendanceByBatchSectionSem(
            String batch, String section, String sem
    ) {

        SemWiseSubjectAttendance response=new SemWiseSubjectAttendance();

        List<SubjectAttendanceResponse> subjectAttendanceResponses =
                getSubjectAttendanceResponse(batch, section, sem);

        response.setSubjectAttendances(subjectAttendanceResponses);
        response.setStatusCode(HttpStatus.OK);

        return response;
    }

    private List<SubjectAttendanceResponse> getSubjectAttendanceResponse(String batch, String section, String sem) {
        List<Subject> subjectResponses = subjectRepo.findAllByBatchAndSem(batch, sem);


        List<SubjectAttendanceResponse> responseList =
                subjectResponses.stream().map(subject -> {


                            Optional<Integer> totalClass =
                                    subjectRecordRepo.getTotalClass(batch, section, subject.getSubCode());

                            int subjectTotalClass = (totalClass.orElse(0));

                            SubjectAttendanceResponse subjectAttendanceResponse =
                                    modelMapper.map(subject, SubjectAttendanceResponse.class);

                            subjectAttendanceResponse.setTotalClass(subjectTotalClass);
                            return subjectAttendanceResponse;
                        }
                ).toList();

        return responseList;
    }


    @Override
    public StudentAttendanceReportResponse getStudentsOverallAttendanceByBatchSectionSem(
            String batch, String section, String sem
    ) {

        StudentAttendanceReportResponse response=new StudentAttendanceReportResponse();

        List<EducationalDetails> educationalDetailsList = educationalRepo.findAllByBatchAndSection(batch, section);

        List<Subject> subjects = subjectRepo.findAllByBatchAndSem(batch, sem);

        int totalSubClasses = 0;

        for (Subject subject : subjects) {
            int curSubTotal = subjectRecordRepo.getTotalClass(batch, section, subject.getSubCode()).orElse(0);
            totalSubClasses += curSubTotal;
        }

        final int totalClasses = totalSubClasses;

        List<StudentOverallAttendanceResponse> studentSubjectAttendanceResponses =
                educationalDetailsList.stream().map(student -> {
                    StudentOverallAttendanceResponse studentOverallAttendanceResponse =
                            modelMapper.map(student, StudentOverallAttendanceResponse.class);

                    int presentClasses = 0;

                    for (Subject subject : subjects) {
                        int curPreset = studentRecordRepo.getTotalClass(student.getCollegeId(), subject.getSubCode()).
                                orElse(0);
                        presentClasses += curPreset;

                    }

                    String percentage = "0.00";

                    if (totalClasses > 0) {
                        double avgPercentage = ((double) presentClasses / totalClasses) * 100;
                        percentage = String.format("%.2f", avgPercentage);
                    }

                    studentOverallAttendanceResponse.setOverallAttendance(percentage);

                    return studentOverallAttendanceResponse;
                }).toList();

        response.setStudentSubjectAttendanceResponses(studentSubjectAttendanceResponses);
        response.setHttpStatus(HttpStatus.OK);

        return response;
    }

    @Override
    public SubjectAttendanceResponseWithDate getSubjectAttendanceResponseWithDateByRollNoOrCollegeId(String id, String subCode,String sem) {

        SubjectAttendanceResponseWithDate response = new SubjectAttendanceResponseWithDate();

        Optional<EducationalDetails> isRollNo = educationalRepo.findByRollNo(id);

        List<SubjectAttendanceStatusWithDate> subjectAttendanceStatusWithDates = null;


        if (isRollNo.isPresent()) {

            EducationalDetails data = isRollNo.get();
            String collegeId = data.getCollegeId();


            if(!isRollNo.get().getSem().equals(sem)){
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                return response;
            }
            //setting properties

            response.setCollegeId(collegeId);
            response.setRollNo(data.getRollNo());
            response.setName(data.getFirstName()+" "+data.getLastName());
            response.setBatch(data.getBatch());
            response.setSection(data.getSection());
            response.setSem(data.getSem());

            subjectAttendanceStatusWithDates =
                    attendanceRepo.getSubjectAttendanceDateWiseByCollegeIdAndSubCode(collegeId, subCode);

            response.setStatusByDateList(subjectAttendanceStatusWithDates);
            response.setStatusCode(HttpStatus.OK);

            return response;
        } else {

            Optional<EducationalDetails> isCollegeId = educationalRepo.findByCollegeId(id);
            if (isCollegeId.isPresent()) {

                if(!isCollegeId.get().getSem().equals(sem)){
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                    return response;
                }

                EducationalDetails data=isCollegeId.get();
                response.setCollegeId(data.getCollegeId());
                response.setRollNo(data.getRollNo());
                response.setName(data.getFirstName()+" "+data.getLastName());
                response.setBatch(data.getBatch());
                response.setSection(data.getSection());
                response.setSem(data.getSem());

                subjectAttendanceStatusWithDates =
                        attendanceRepo.getSubjectAttendanceDateWiseByCollegeIdAndSubCode(id, subCode);

                response.setStatusByDateList(subjectAttendanceStatusWithDates);
                response.setStatusCode(HttpStatus.OK);

                return response;
            }


        }


        response.setStatusCode(HttpStatus.NOT_FOUND);
        return response;

    }

    @Override
    public ResponseEntity<String> updateMedicalAttendance(MedicalAttendanceRequest medicalAttendanceRequest) {

        String collegeId=medicalAttendanceRequest.getCollegeId();
        String subCode= medicalAttendanceRequest.getSubCode();

        medicalAttendanceRequest.getAttendanceRequests().stream().forEach(attendance->{

            boolean curStatus=attendance.isCurStatus();
            boolean status=attendance.isPrevStatus();

            if(curStatus!=status) {
                studentRecordRepo.findByCollegeIdAndSubCode(collegeId, subCode)
                        .ifPresent(subjectRecord -> {
                            int curClass = subjectRecord.getTotalClass();

                            if (!status && curStatus) {
                                subjectRecord.setTotalClass(curClass + 1);

                            } else if (status && !curStatus) {
                                subjectRecord.setTotalClass(curClass - 1);
                            }


                            studentRecordRepo.save(subjectRecord);
                        });

                attendanceRepo.updateAttendanceStatus(collegeId, subCode, attendance.getDate(), attendance.getClassType(),
                        attendance.isCurStatus());

            }

        });

       return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
    }

}
