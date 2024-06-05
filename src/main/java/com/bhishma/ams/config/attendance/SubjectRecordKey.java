package com.bhishma.ams.config.attendance;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectRecordKey {
    private String batch;
    private String subCode;
    private String section;
}
