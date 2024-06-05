package com.bhishma.ams.response;

import com.bhishma.ams.config.Role;
import lombok.Data;

@Data
public class FacultyResponse {
    private String mailId;
    private String name;
    private String shortName;
}