package com.bhishma.ams.repository.student;

import com.bhishma.ams.entity.student.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarksRepo extends JpaRepository<Marks, String> {
}
