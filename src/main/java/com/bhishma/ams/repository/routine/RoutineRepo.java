package com.bhishma.ams.repository.routine;

import com.bhishma.ams.entity.routine.Routine;
import com.bhishma.ams.entity.routine.RoutineKey;
import com.bhishma.ams.response.routine.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RoutineRepo extends JpaRepository<Routine, RoutineKey> {

    @Query("select new com.bhishma.ams.response.routine.ClassResponse(r.subCode, f.shortName, r.startTime, r.endTime, r.roomNo, r.classType, r.day) " +
            "from Routine r inner join Faculty f on r.teacherId = f.mailId " +
            "where r.batch = :batch and r.sem = :sem and r.section = :section")
    List<ClassResponse> getClassDetailsByBatchSemSection(String batch, String sem, String section);

    @Query("SELECT DISTINCT new com.bhishma.ams.response.routine.SubjectReferenceResponse(s.subCode, s.subName) " +
            "FROM Subject s " +
            "INNER JOIN Routine r ON s.batch = r.batch AND s.sem = r.sem AND s.subCode = r.subCode " +
            "WHERE r.section = :section AND r.sem = :sem AND r.batch = :batch")
    List<SubjectReferenceResponse> getSubjectNameWithCode(@Param("batch") String batch,
                                                          @Param("sem") String sem,
                                                          @Param("section") String section);

    @Query("select distinct new com.bhishma.ams.response.routine.TeacherReferenceResponse(t.shortName, t.name) " +
            "from Faculty t inner join Routine r on t.mailId = r.teacherId " +
            "where r.batch = :batch and r.sem = :sem and r.section = :section")
    List<TeacherReferenceResponse> getTeacherShortNameWithFullName(String batch, String sem, String section);

    @Query("SELECT c.sem FROM CurrentSem c WHERE c.batch IN :batches")
    List<String> findSemestersByBatches(@Param("batches") List<String> batches);


    @Query(value = "SELECT r.sub_code, r.start_time, r.end_time, r.room_no, r.section, r.class_type, r.day " +
            "FROM routine r " +
            "JOIN (VALUES (?1, ?2), (?3, ?4), (?5, ?6), (?7, ?8)) AS batch_sem (batch, sem) " +
            "ON r.batch = batch_sem.batch AND r.sem = batch_sem.sem " +
            "WHERE r.teacher_id = ?9",
            nativeQuery = true)
    List<Object[]> findClassResponsesByTeacherIdAndIndexWiseSemAndBatchRaw(String batch1, String sem1,
                                                                           String batch2, String sem2,
                                                                           String batch3, String sem3,
                                                                           String batch4, String sem4,
                                                                           String teacherId);

    default List<ClassResponseTeacher> findClassResponsesByTeacherIdAndIndexWiseSemAndBatch(String batch1, String sem1,
                                                                                            String batch2, String sem2,
                                                                                            String batch3, String sem3,
                                                                                            String batch4, String sem4,
                                                                                            String teacherId) {
        List<Object[]> results = findClassResponsesByTeacherIdAndIndexWiseSemAndBatchRaw(batch1, sem1, batch2, sem2, batch3, sem3, batch4, sem4, teacherId);
        List<ClassResponseTeacher> classResponses = new ArrayList<>();
        for (Object[] result : results) {
            classResponses.add(new ClassResponseTeacher(
                    (String) result[0],
                    (String) result[1],
                    (String) result[2],
                    (String) result[3],
                    (String) result[4],
                    (String) result[5],
                    (String) result[6]
            ));
        }
        return classResponses;
    }


    @Query(value = "SELECT DISTINCT s.sub_code AS subCode, s.sub_name AS subName " +
            "FROM subject_data s " +
            "INNER JOIN routine r ON s.batch = r.batch AND s.sem = r.sem " +
            "WHERE (s.batch = :batch1 AND s.sem = :sem1) " +  // Index 0
            "   OR (s.batch = :batch2 AND s.sem = :sem2) " +  // Index 1
            "   OR (s.batch = :batch3 AND s.sem = :sem3) " +  // Index 2
            "   OR (s.batch = :batch4 AND s.sem = :sem4) " +  // Index 3
            "   AND r.teacher_id = :teacherId",
            nativeQuery = true)
    List<Object[]> getTeacherSubjectNameWithCodeRaw(@Param("teacherId") String teacherId,
                                                    @Param("batch1") String batch1, @Param("sem1") String sem1,
                                                    @Param("batch2") String batch2, @Param("sem2") String sem2,
                                                    @Param("batch3") String batch3, @Param("sem3") String sem3,
                                                    @Param("batch4") String batch4, @Param("sem4") String sem4);

    default List<SubjectReferenceResponse> getTeacherSubjectNameWithCode(String teacherId, List<String> batches, List<String> semesters) {
        String batch1 = batches.get(0);
        String sem1 = semesters.get(0);
        String batch2 = batches.get(1);
        String sem2 = semesters.get(1);
        String batch3 = batches.get(2);
        String sem3 = semesters.get(2);
        String batch4 = batches.get(3);
        String sem4 = semesters.get(3);

        List<Object[]> results = getTeacherSubjectNameWithCodeRaw(teacherId, batch1, sem1, batch2, sem2, batch3, sem3, batch4, sem4);
        List<SubjectReferenceResponse> subjectReferences = new ArrayList<>();
        for (Object[] result : results) {
            subjectReferences.add(new SubjectReferenceResponse(
                    (String) result[0],   // subCode
                    (String) result[1]    // subName
            ));
        }
        return subjectReferences;
    }



    @Query("SELECT new com.bhishma.ams.response.routine.ClassResponseRoomNo(r.subCode, f.shortName, r.startTime, r.endTime, r.section, r.classType, r.day) " +
            "FROM Routine r inner join Faculty f on r.teacherId = f.mailId " +
            "WHERE r.roomNo = :roomNo AND " +
            "      ((r.batch = :batch1 AND r.sem = :sem1) " +
            "       OR (r.batch = :batch2 AND r.sem = :sem2) " +
            "       OR (r.batch = :batch3 AND r.sem = :sem3) " +
            "       OR (r.batch = :batch4 AND r.sem = :sem4))")
    List<ClassResponseRoomNo> findClassResponsesByRoomNoBatchSem(String roomNo,
                                                                 String batch1,String sem1,
                                                                 String batch2, String sem2,
                                                                 String batch3,String sem3,
                                                                 String batch4,String sem4);


    @Query("SELECT DISTINCT new com.bhishma.ams.response.routine.SubjectReferenceResponse(s.subCode, s.subName) " +
            "FROM Subject s " +
            "INNER JOIN Routine r ON s.batch = r.batch AND s.sem = r.sem AND s.subCode = r.subCode " +
            "WHERE r.roomNo = :roomNo AND " +
            "      ((r.batch = :batch1 AND r.sem = :sem1) " +
            "       OR (r.batch = :batch2 AND r.sem = :sem2) " +
            "       OR (r.batch = :batch3 AND r.sem = :sem3) " +
            "       OR (r.batch = :batch4 AND r.sem = :sem4))")
    List<SubjectReferenceResponse> findSubjectNameWithCodeBYRoomNoBatchSem(String roomNo,
                                                                           String batch1, String sem1,
                                                                           String batch2, String sem2,
                                                                           String batch3, String sem3,
                                                                           String batch4, String sem4);
    @Query("select distinct new com.bhishma.ams.response.routine.TeacherReferenceResponse(t.shortName, t.name) " +
            " from Faculty t inner join Routine r on t.mailId = r.teacherId " +
            "WHERE r.roomNo = :roomNo AND " +
            "      ((r.batch = :batch1 AND r.sem = :sem1) " +
            "       OR (r.batch = :batch2 AND r.sem = :sem2) " +
            "       OR (r.batch = :batch3 AND r.sem = :sem3) " +
            "       OR (r.batch = :batch4 AND r.sem = :sem4))")
    List<TeacherReferenceResponse> findTeacherNameByRoomNoBatchSem(String roomNo,
                                                                   String batch1, String sem1,
                                                                   String batch2, String sem2,
                                                                   String batch3, String sem3,
                                                                   String batch4, String sem4);



    @Query("SELECT DISTINCT new com.bhishma.ams.response.routine.RoutineSubject(s.subCode, s.subName,r.classType," +
            "r.batch,r.sem,r.section,'P') " +
            "FROM Subject s " +
            "INNER JOIN Routine r ON s.batch = r.batch AND s.sem = r.sem AND s.subCode = r.subCode " +
            "WHERE r.teacherId = :teacherId AND r.day=:day AND " +
            "      ((r.batch = :batch1 AND r.sem = :sem1) " +
            "       OR (r.batch = :batch2 AND r.sem = :sem2) " +
            "       OR (r.batch = :batch3 AND r.sem = :sem3) " +
            "       OR (r.batch = :batch4 AND r.sem = :sem4))")
    List<RoutineSubject> getTeacherWiseSubjectByDay(String day, String teacherId,
                                                    String batch1, String sem1,
                                                    String batch2, String sem2,
                                                    String batch3, String sem3,
                                                    String batch4, String sem4);

    @Modifying
    @Transactional
    @Query("UPDATE Routine r SET r.subTeacherId=:subTeacherId, r.lastUpdateTime=:dateTime" +
            " WHERE r.batch=:batch AND r.sem=:sem AND r.section=:section AND r.subCode=:subCode" +
            " AND r.classType=:classType AND r.day=:day")
    void updateSubstituteTeacher(String batch, String sem, String section, String subCode,
                                 String subTeacherId, String classType,String day,String dateTime);

    @Query("SELECT DISTINCT new com.bhishma.ams.response.routine.RoutineSubject(s.subCode, s.subName,r.classType," +
            "r.batch,r.sem,r.section,'S') " +
            "FROM Subject s " +
            "INNER JOIN Routine r ON s.batch = r.batch AND s.sem = r.sem AND s.subCode = r.subCode " +
            "WHERE r.subTeacherId = :teacherId AND r.day=:day AND r.lastUpdateTime>=:dateTime AND" +
            "      ((r.batch = :batch1 AND r.sem = :sem1) " +
            "       OR (r.batch = :batch2 AND r.sem = :sem2) " +
            "       OR (r.batch = :batch3 AND r.sem = :sem3) " +
            "       OR (r.batch = :batch4 AND r.sem = :sem4))")
    List<RoutineSubject> findSubstitudeSubjectByTeacherIdDay(String day, String teacherId, String dateTime,
                                                             String batch1, String sem1,
                                                             String batch2, String sem2,
                                                             String batch3, String sem3,
                                                             String batch4, String sem4);

    @Query("SELECT DISTINCT new com.bhishma.ams.response.routine.SubjectDetails(s.subCode, s.subName,"+
            "r.sem,r.section) " +
            "FROM Subject s " +
            "INNER JOIN Routine r ON s.batch = r.batch AND s.sem = r.sem AND s.subCode = r.subCode " +
            "WHERE r.teacherId = :teacherId  AND" +
            "      ((r.batch = :batch1 AND r.sem = :sem1) " +
            "       OR (r.batch = :batch2 AND r.sem = :sem2) " +
            "       OR (r.batch = :batch3 AND r.sem = :sem3) " +
            "       OR (r.batch = :batch4 AND r.sem = :sem4))")
    List<SubjectDetails> getAllSubjectByTeacherId(String teacherId,
                                                  String batch1, String sem1,
                                                  String batch2, String sem2,
                                                  String batch3, String sem3,
                                                  String batch4, String sem4);

  Optional<Routine> findByBatchAndSemAndSectionAndSubCodeAndDayAndStartTime(String batch, String section, String section1, String subCode, String day, String startTime);
}
