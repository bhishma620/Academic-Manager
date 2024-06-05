package com.bhishma.ams.request.student;

import com.bhishma.ams.entity.student.*;
import lombok.Data;

@Data
public class StudentDetailsRequest {

    private String collegeId;
    private EducationalDetails educationalDetails;
    private PersonalInformation personalInformation;
    private CurrentContact currentContact;
    private AddressInformation addressInformation;
    private GuardianDetails guardianDetails;
    private Marks marks;
    private SemesterMarks semesterMarks;
    private CurrentCoOrdinate currentCoOrdinate;
}
