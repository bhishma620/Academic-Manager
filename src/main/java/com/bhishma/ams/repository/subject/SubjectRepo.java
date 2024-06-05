package com.bhishma.ams.repository.subject;


import com.bhishma.ams.config.subject.SubjectKey;
import com.bhishma.ams.entity.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, SubjectKey> {


    Optional<Subject> findByBatchAndSubCode(String batch,String subCode);

    List<Subject> findAllByBatchAndSem(String batch, String sem);


}
