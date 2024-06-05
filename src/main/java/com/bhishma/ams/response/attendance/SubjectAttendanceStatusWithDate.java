package com.bhishma.ams.response.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SubjectAttendanceStatusWithDate implements Serializable {
    private String date;
    private boolean status;
    private String classType;
}
