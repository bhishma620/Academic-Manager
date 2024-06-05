package com.bhishma.ams.entity.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class SemesterMarks {
    @Id
    private String collegeId;
    private String sem1;
    private String sem2;
    private String sem3;
    private String sem4;
    private String sem5;
    private String sem6;
    private String sem7;
    private String sem8;

}
