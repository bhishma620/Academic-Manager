package com.bhishma.ams.request.attendance;

import lombok.Data;

@Data
public class PresentStatusRequest {
    private String collegeId;
    private boolean status;
}
