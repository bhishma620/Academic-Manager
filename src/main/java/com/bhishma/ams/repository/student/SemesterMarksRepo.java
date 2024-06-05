package com.bhishma.ams.repository.student;

import com.bhishma.ams.entity.student.SemesterMarks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterMarksRepo extends JpaRepository<SemesterMarks, String> {
}
