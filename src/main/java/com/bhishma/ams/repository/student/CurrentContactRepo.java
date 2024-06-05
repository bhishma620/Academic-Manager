package com.bhishma.ams.repository.student;

import com.bhishma.ams.entity.student.CurrentContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentContactRepo extends JpaRepository<CurrentContact, String> {

}
