package com.bhishma.ams.service.impl.routine;

import com.bhishma.ams.entity.routine.Routine;
import com.bhishma.ams.repository.CurrentSemRepo;
import com.bhishma.ams.repository.routine.RoutineRepo;
import com.bhishma.ams.request.routine.RoutineRequest;
import com.bhishma.ams.request.routine.RoutineUpdateRequest;
import com.bhishma.ams.response.routine.*;
import com.bhishma.ams.service.routine.RoutineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RoutineServiceImpl implements RoutineService {

    @Autowired
    RoutineRepo routineRepo;

    @Autowired
    CurrentSemRepo currentSemRepo;

    @Autowired
    ModelMapper modelMapper;

    @Value("${ams-authentication.substitute-teacher-validation-time}")
    private long substituteTeacherValidtionTime;

    @Override
    public ResponseEntity<String> addRoutine(RoutineRequest routineRequest) {

        if (routineRepo.findByBatchAndSemAndSectionAndSubCodeAndDayAndStartTime(routineRequest.getBatch(),
                routineRequest.getSem(), routineRequest.getSection(), routineRequest.getSubCode(),
                routineRequest.getDay(), routineRequest.getStartTime()).isPresent()) {
            return new ResponseEntity<>("Already Exist ,Please update", HttpStatus.NOT_ACCEPTABLE);
        }

        Routine routine = modelMapper.map(routineRequest, Routine.class);
        routineRepo.save(routine);
        return ResponseEntity.ok("Added Succesfully");
    }

    @Override
    public SectionWiseRoutineResponse getSectionWiseRoutine(String batch, String section) {

        SectionWiseRoutineResponse response = new SectionWiseRoutineResponse();

        String sem = currentSemRepo.findSemByBatch(batch);

        if (sem == null) {
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return response;
        }

        List<SubjectReferenceResponse> subjectReferences = routineRepo.getSubjectNameWithCode(batch, sem, section);

        List<TeacherReferenceResponse> teacherReferences = routineRepo.getTeacherShortNameWithFullName(batch, sem, section);

        List<ClassResponse> classResponses = routineRepo.getClassDetailsByBatchSemSection(batch, sem, section);

        Map<String, List<ClassResponse>> weekDetails = new HashMap<>();


        for (ClassResponse classResponse : classResponses) {

            List<ClassResponse> cur = weekDetails.getOrDefault(classResponse.getDay(),
                    new ArrayList<>());

            cur.add(classResponse);

            weekDetails.put(classResponse.getDay(), cur);
        }

        response.setWeekDetails(weekDetails);
        response.setTeacherReferences(teacherReferences);
        response.setSubjectReferences(subjectReferences);

        response.setStatusCode(HttpStatus.OK);

        return response;
    }

    @Override
    public TeacherWiseRoutineResponse getTeacherWiseRoutine(String teacherId) {

        TeacherWiseRoutineResponse response = new TeacherWiseRoutineResponse();

        List<String> batches = getCurrentBatches();

        List<String> semesters = routineRepo.findSemestersByBatches(batches);

        List<ClassResponseTeacher> classResponses =
                routineRepo.findClassResponsesByTeacherIdAndIndexWiseSemAndBatch(batches.get(0), semesters.get(0),
                        batches.get(1), semesters.get(1), batches.get(2), semesters.get(2), batches.get(3),
                        semesters.get(3), teacherId);


        List<SubjectReferenceResponse> subjectReferences =
                routineRepo.getTeacherSubjectNameWithCode(teacherId, batches, semesters);

        Map<String, List<ClassResponseTeacher>> weekDetails = new HashMap<>();

        for (ClassResponseTeacher classResponse : classResponses) {

            List<ClassResponseTeacher> cur = weekDetails.getOrDefault(classResponse.getDay(),
                    new ArrayList<>());

            cur.add(classResponse);

            weekDetails.put(classResponse.getDay(), cur);
        }

        response.setSubjectReferenceResponses(subjectReferences);
        response.setWeekDetails(weekDetails);
        response.setStatusCode(HttpStatus.OK);

        return response;


    }


    private List<String> getCurrentBatches() {


        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

        int monthNumber = Integer.parseInt(currentDate.format(monthFormatter));

        int year = Integer.parseInt(currentDate.format(yearFormatter));

        int batchStart = year - 4;


        List<String> batches = new ArrayList<>(Arrays.asList(batchStart + "-" + year, (batchStart + 1) + "-" + (year + 1), (batchStart + 2) + "-" + (year + 2),
                (batchStart + 3) + "-" + (year + 3), (batchStart + 4) + "-" + (year + 4)));


        int r = (monthNumber > 6 ? 5 : 4);
        int s = (monthNumber > 6 ? 1 : 0);

        return batches.subList(s, r);
    }

    @Override
    public RoomWiseRoutineResponse getRoomWiseRoutine(String roomNo) {

        RoomWiseRoutineResponse response = new RoomWiseRoutineResponse();

        List<String> batches = getCurrentBatches();
        List<String> semesters = routineRepo.findSemestersByBatches(batches);

        List<ClassResponseRoomNo> classResponses = routineRepo.findClassResponsesByRoomNoBatchSem(roomNo,
                batches.get(0), semesters.get(0),
                batches.get(1), semesters.get(1), batches.get(2), semesters.get(2), batches.get(3),
                semesters.get(3));


        List<SubjectReferenceResponse> subjectReferences = routineRepo.findSubjectNameWithCodeBYRoomNoBatchSem(roomNo,
                batches.get(0), semesters.get(0),
                batches.get(1), semesters.get(1), batches.get(2), semesters.get(2), batches.get(3),
                semesters.get(3));

        List<TeacherReferenceResponse> teacherReferences = routineRepo.findTeacherNameByRoomNoBatchSem(roomNo,
                batches.get(0), semesters.get(0),
                batches.get(1), semesters.get(1), batches.get(2), semesters.get(2), batches.get(3),
                semesters.get(3));

        Map<String, List<ClassResponseRoomNo>> weekDetails = new HashMap<>();

        for (ClassResponseRoomNo classResponse : classResponses) {

            List<ClassResponseRoomNo> cur = weekDetails.getOrDefault(classResponse.getDay(),
                    new ArrayList<>());

            cur.add(classResponse);

            weekDetails.put(classResponse.getDay(), cur);
        }

        response.setWeekDetails(weekDetails);

        response.setSubjectReferences(subjectReferences);

        response.setTeacherReferences(teacherReferences);
        response.setStatusCode(HttpStatus.OK);

        return response;


    }

    @Override
    public TeacherSubjectResponse getTeacherWiseOwnSubjectByDay(String day, String teacherId) {
        List<String> batches = getCurrentBatches();
        List<String> semesters = routineRepo.findSemestersByBatches(batches);

        List<RoutineSubject> subjects = getPrimaryTeacherSubjects(day, teacherId, batches, semesters);

        TeacherSubjectResponse response = new TeacherSubjectResponse();

        response.setSubjectList(subjects);
        response.setStatusCode(HttpStatus.OK);
        return response;

    }

    private List<RoutineSubject> getPrimaryTeacherSubjects(String day, String teacherId, List<String> batches,
                                                           List<String> semesters) {

        List<RoutineSubject> subjects = routineRepo.getTeacherWiseSubjectByDay(day, teacherId, batches.get(0),
                semesters.get(0), batches.get(1), semesters.get(1), batches.get(2), semesters.get(2), batches.get(3),
                semesters.get(3));
        return subjects;
    }

    @Override
    public ResponseEntity<String> addSubstituteTeacher(String batch, String sem, String section, String subCode,
                                                       String classType, String day, String subTeacherId) {


        LocalDateTime currentDateTime1 = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateTime = currentDateTime1.format(formatter);


        routineRepo.updateSubstituteTeacher(batch, sem, section, subCode, subTeacherId, classType, day, dateTime);
        return new ResponseEntity<>("Updated Succesfully", HttpStatus.OK);

    }

    @Override
    public TeacherSubjectResponse getTeacherWiseAllSubjectByDay(String day, String teacherId) {

        TeacherSubjectResponse response = new TeacherSubjectResponse();

        List<String> batches = getCurrentBatches();
        List<String> semesters = routineRepo.findSemestersByBatches(batches);


        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime targetDateTime = currentDateTime.minus(substituteTeacherValidtionTime, ChronoUnit.SECONDS);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateTime = targetDateTime.format(formatter);

        List<RoutineSubject> primaryTeachersubjects = getPrimaryTeacherSubjects(day, teacherId, batches, semesters);


        List<RoutineSubject> substitudeTeacherSubjects =
                routineRepo.findSubstitudeSubjectByTeacherIdDay(day, teacherId, dateTime, batches.get(0),
                        semesters.get(0), batches.get(1), semesters.get(1), batches.get(2), semesters.get(2), batches.get(3),
                        semesters.get(3));

        List<RoutineSubject> subjects = new ArrayList<>();
        subjects.addAll(primaryTeachersubjects);
        subjects.addAll(substitudeTeacherSubjects);

        response.setSubjectList(subjects);
        response.setStatusCode(HttpStatus.OK);

        return response;
    }

    @Override
    public TeacherWeekWiseSubjectResponse getAllSubjectByTeacherId(String teacherId) {

        TeacherWeekWiseSubjectResponse response = new TeacherWeekWiseSubjectResponse();

        List<String> batches = getCurrentBatches();
        List<String> semesters = routineRepo.findSemestersByBatches(batches);

        List<SubjectDetails> subjects = routineRepo.getAllSubjectByTeacherId(teacherId, batches.get(0),
                semesters.get(0), batches.get(1), semesters.get(1), batches.get(2), semesters.get(2), batches.get(3),
                semesters.get(3));

        response.setSubjects(subjects);
        response.setStatusCode(HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<String> updateRoutine(RoutineUpdateRequest updateRequest) {

        String batch = updateRequest.getBatch();

        String sem = updateRequest.getSem();

        String section = updateRequest.getSection();

        String day = updateRequest.getDay();

        String subCode = updateRequest.getSubCode();

        String startTime = updateRequest.getStartTime();

        String endTime = updateRequest.getEndTime();

        String curStartTime = updateRequest.getCurStartTime();

        String curEndTime = updateRequest.getCurEndTime();

        String classType = updateRequest.getClassType();

        String roomNo = updateRequest.getRoomNo();

        String teacherId=updateRequest.getTeacherId();

        if(routineRepo.findByBatchAndSemAndSectionAndSubCodeAndDayAndStartTime(batch,sem,section,subCode,day,startTime).isPresent()){

            Routine routine=modelMapper.map(updateRequest,Routine.class);
            routine.setStartTime(curStartTime);
            routine.setEndTime(curEndTime);
            routineRepo.save(routine);

        }
        else{
            return new ResponseEntity<>("Routine is not valid",HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>("Updated", HttpStatus.OK);

    }


}
