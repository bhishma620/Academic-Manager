package com.bhishma.ams.entity.attendance;

import com.bhishma.ams.config.attendance.AttendanceStatusOnDateKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AttendanceStatusOnDateKey.class)
public class AttendanceStatusOnDate {
    @Id
    private String batch;
    @Id
    private String section;
    @Id
    private String subCode;
    @Id
    private String date;
    @Id
    private String classType;
}
