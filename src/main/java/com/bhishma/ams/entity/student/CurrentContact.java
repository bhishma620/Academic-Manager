package com.bhishma.ams.entity.student;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CurrentContact {
    @Id
    private String collegeId;
    private String mobileNo;
    private String email;
}
