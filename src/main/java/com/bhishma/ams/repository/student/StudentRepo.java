package com.bhishma.ams.repository.student;

import com.bhishma.ams.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,String> {
    Optional<Student> findByCollegeId(String collegeId);

}
