package com.bhishma.ams.request.student;

import lombok.Data;

@Data
public class CurrentContactUpdateRequest {
    private String mobileNo;
    private String email;
}
