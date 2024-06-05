package com.bhishma.ams.repository.attendance;

import com.bhishma.ams.config.attendance.AttendanceStatusOnDateKey;
import com.bhishma.ams.entity.attendance.AttendanceStatusOnDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceStatusOnDateRepo extends JpaRepository<AttendanceStatusOnDate,
        AttendanceStatusOnDateKey> {


    Optional<AttendanceStatusOnDate> findByBatchAndSectionAndSubCodeAndDateAndClassType(String batch,
                                                                            String section, String subCode,
                                                                            String date,String classType);
}
