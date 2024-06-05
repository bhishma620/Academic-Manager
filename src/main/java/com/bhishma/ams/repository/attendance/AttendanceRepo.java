package com.bhishma.ams.repository.attendance;

import com.bhishma.ams.config.attendance.AttendanceKey;
import com.bhishma.ams.entity.attendance.Attendance;
import com.bhishma.ams.response.attendance.PresentStatusResponse;
import com.bhishma.ams.response.attendance.SubjectAttendanceStatusWithDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, AttendanceKey> {

    @Query(nativeQuery = true, value = "SELECT status FROM attendance " +
            "WHERE college_id = :collegeId AND sub_code = :subCode AND date = :date AND class_type= :classType")
    Optional<Boolean> findAttendanceStatus(String collegeId, String subCode, String date, String classType);

    @Modifying
    @Transactional
    @Query("UPDATE Attendance a SET a.status = :status WHERE a.collegeId = :collegeId AND a.subCode = :subCode " +
            "AND a.date = :date AND a.classType = :classType")
    void updateAttendanceStatus(@Param("collegeId") String collegeId,
                                @Param("subCode") String subCode,
                                @Param("date") String date,
                                @Param("classType") String classType,
                                @Param("status") boolean status);
    @Query("SELECT  new com.bhishma.ams.response.attendance.SubjectAttendanceStatusWithDate(a.date,a.status,a.classType)" +
            "FROM Attendance a WHERE a.collegeId=:collegeId AND a.subCode=:subCode")
    List<SubjectAttendanceStatusWithDate> getSubjectAttendanceDateWiseByCollegeIdAndSubCode(String collegeId, String subCode);

    @Query("SELECT new com.bhishma.ams.response.attendance.PresentStatusResponse(e.collegeId, e.rollNo, e.firstName,e.lastName, sr.totalClass, '0', a.status) " +
            "FROM Attendance a " +
            "INNER JOIN EducationalDetails e ON a.collegeId = e.collegeId " +
            "INNER JOIN StudentRecord sr ON a.collegeId = sr.collegeId AND a.subCode = sr.subCode " +
            "WHERE e.section = :section AND e.batch = :batch AND a.subCode = :subCode AND a.date = :date " +
            "AND a.classType = :classType ORDER BY e.rollNo")
    List<PresentStatusResponse> getAttendanceStatusTotalClassByBatchSectionSubCodeDateClassType(
            @Param("batch") String batch,
            @Param("section") String section,
            @Param("subCode") String subCode,
            @Param("date") String date,
            @Param("classType") String classType);

    @Query("SELECT new com.bhishma.ams.response.attendance.PresentStatusResponse(e.collegeId, e.rollNo, e.firstName,e.lastName, sr.totalClass, '0', false) " +
            "FROM EducationalDetails e  " +
            "INNER JOIN StudentRecord sr ON e.collegeId = sr.collegeId " +
            "WHERE sr.subCode = :subCode ORDER By e.rollNo")
    List<PresentStatusResponse> getStudentWithTotalClassByBatchSectionSubCodeDateClassType(
            @Param("subCode") String subCode);

    @Query("SELECT new com.bhishma.ams.response.attendance.PresentStatusResponse(e.collegeId, e.rollNo, e.firstName,e.lastName, 0, '0', false) " +
            "FROM EducationalDetails e ORDER BY e.rollNo")
    List<PresentStatusResponse> getStudentForFirstClassByBatchAndSection(@Param("batch") String batch,
                                                                         @Param("section") String section);
}
