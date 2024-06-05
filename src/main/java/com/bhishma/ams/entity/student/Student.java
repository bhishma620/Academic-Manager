package com.bhishma.ams.entity.student;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Student_Data")

public class Student {
    @Id
    private String collegeId;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EducationalDetails educationalDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private PersonalInformation personalInformation;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CurrentContact currentContact;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CurrentCoOrdinate currentCoOrdinate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private AddressInformation addressInformation;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private GuardianDetails guardianDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Marks marks;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private SemesterMarks semesterMarks;
}

