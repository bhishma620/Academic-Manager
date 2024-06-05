package com.bhishma.ams.repository.student;

import com.bhishma.ams.entity.student.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInformationRepo extends JpaRepository<PersonalInformation, String> {
}
