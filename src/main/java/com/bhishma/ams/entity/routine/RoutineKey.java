package com.bhishma.ams.entity.routine;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoutineKey {
    private String batch;
    private String sem;
    private String section;

    private String day;

    private String startTime;
}
