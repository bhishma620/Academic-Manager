package com.bhishma.ams.entity.routine;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity
@Data
@IdClass(RoutineKey.class)
public class Routine {

    @Id
    private String batch;
    @Id
    private String sem;
    @Id
    private String section;

    @Id
    private String day;

    @Id
    private String startTime;

    private String endTime;

    private String classType;

    private String roomNo;

    private String subCode;

    private String teacherId;

    private String subTeacherId;

    private String lastUpdateTime;

}
