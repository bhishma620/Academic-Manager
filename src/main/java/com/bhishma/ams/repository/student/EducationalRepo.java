package com.bhishma.ams.repository.student;

import com.bhishma.ams.entity.student.EducationalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EducationalRepo extends JpaRepository<EducationalDetails, String> {

    Optional<EducationalDetails> findByRollNo(String roll);

    Optional<EducationalDetails>findByCollegeId(String id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "UPDATE educational_details SET sem = :newSem WHERE batch = :batch")
    void upgradeSemester(String batch,String newSem);

    List<EducationalDetails> findAllByBatchAndSection(String batch, String section);

    Page<EducationalDetails> findAllByBatchAndSection(String batch, String section, Pageable pageable);

}
