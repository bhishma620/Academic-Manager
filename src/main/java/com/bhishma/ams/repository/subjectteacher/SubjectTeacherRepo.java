package com.bhishma.ams.repository.subjectteacher;


import com.bhishma.ams.config.subjectteacher.SubjectTeacherKey;
import com.bhishma.ams.entity.subjectteacher.SubjectTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectTeacherRepo extends JpaRepository<SubjectTeacher, SubjectTeacherKey> {

    Optional<SubjectTeacher> findByBatchAndSubCodeAndSection(String batch, String subCode,String section);
}
