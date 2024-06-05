package com.bhishma.ams.entity.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Marks {
    @Id
    private String collegeId;

    private  String matricPercentage;
    private  String hsPercentage;
    private String diplomaPercentage;


    private String englishTotal;
    private String englishObtained;
    private String physicsTotal;
    private String physicsObtained;

    private String chemistryTotal;
    private String chemistryObtained;

    private String mathTotal;
    private String mathObtained;







}
