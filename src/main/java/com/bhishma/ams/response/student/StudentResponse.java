package com.bhishma.ams.response.student;

import com.bhishma.ams.entity.student.*;
import lombok.Data;

@Data
public class StudentResponse {
    private String collegeId;
    private EducationalDetails educationalDetails;
    private PersonalInformation personalInformation;
    private CurrentContact currentContact;
    private AddressInformation addressInformation;
    private CurrentCoOrdinate currentCoOrdinate;
    private GuardianDetails guardianDetails;
    private Marks marks;
    private SemesterMarks semesterMarks;
}
