package com.bhishma.ams.entity.attendance;

import com.bhishma.ams.config.attendance.AttendanceKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(AttendanceKey.class)
public class Attendance {
    @Id
    private String collegeId;
    @Id
    private String date;
    @Id
    private String subCode;

    @Id
    private String classType;

    private  Boolean status;
}
