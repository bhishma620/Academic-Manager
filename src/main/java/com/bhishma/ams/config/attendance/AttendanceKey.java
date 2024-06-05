package com.bhishma.ams.config.attendance;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttendanceKey {
    private String collegeId;
    private String date;
    private String subCode;
    private String classType;
}
