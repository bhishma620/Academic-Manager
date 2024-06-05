package com.bhishma.ams.repository.student;

import com.bhishma.ams.entity.student.AddressInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressInformationRepo extends JpaRepository<AddressInformation, String> {
}
