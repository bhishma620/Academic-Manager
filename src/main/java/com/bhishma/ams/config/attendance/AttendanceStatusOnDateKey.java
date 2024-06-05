package com.bhishma.ams.config.attendance;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttendanceStatusOnDateKey {
    private String batch;
    private String section;
    private String subCode;
    private String date;
    private String classType;
}
