package com.bhishma.ams.repository.attendance;

import com.bhishma.ams.config.attendance.SubjectRecordKey;
import com.bhishma.ams.entity.attendance.SubjectRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubjectRecordRepo extends JpaRepository<SubjectRecord, SubjectRecordKey> {
    Optional<SubjectRecord> findByBatchAndSectionAndSubCode(String batch, String section, String subCode);

    @Query(nativeQuery = true,
        value = "SELECT total_class FROM subject_record WHERE batch = :batch " +
                "AND section = :section AND sub_code = :subCode")
    Optional<Integer> getTotalClass( String batch,String section,String subCode);

}
