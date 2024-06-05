package com.bhishma.ams.entity.attendance;

import com.bhishma.ams.config.attendance.StudentRecordKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@IdClass(StudentRecordKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class StudentRecord {
    @Id
    private String collegeId;
    @Id
    private String subCode;
    private int totalClass;
}
