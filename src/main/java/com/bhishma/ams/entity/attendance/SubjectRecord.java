package com.bhishma.ams.entity.attendance;

import com.bhishma.ams.config.attendance.SubjectRecordKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@IdClass(SubjectRecordKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRecord {
    @Id
    private String batch;
    @Id
    private String subCode;
    @Id
    private String section;

    private int totalClass;
}
