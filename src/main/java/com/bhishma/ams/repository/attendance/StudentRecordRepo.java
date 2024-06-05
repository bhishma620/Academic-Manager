package com.bhishma.ams.repository.attendance;

import com.bhishma.ams.config.attendance.StudentRecordKey;
import com.bhishma.ams.entity.attendance.StudentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRecordRepo extends JpaRepository<StudentRecord, StudentRecordKey> {

    Optional<StudentRecord> findByCollegeIdAndSubCode(String collegeId, String subCode);



    @Query(nativeQuery = true,
            value = "SELECT total_class FROM student_record WHERE college_id = :collegeId AND sub_code = :subCode")
    Optional<Integer> getTotalClass(String collegeId, String subCode);
}
