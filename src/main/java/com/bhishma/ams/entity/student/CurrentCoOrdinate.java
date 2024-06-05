package com.bhishma.ams.entity.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CurrentCoOrdinate {
    @Id
    private String collegeId;
    private String presentAddress;
    private String presentCity;
    private String presentState;
    private String presentPin;

}
