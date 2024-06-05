package com.bhishma.ams.entity.subjectteacher;

import com.bhishma.ams.config.subjectteacher.SubjectTeacherKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@IdClass(SubjectTeacherKey.class)
@Table(name="SubjectTeacher_Data")
public class SubjectTeacher {

    @Id
    private String batch;

    @Id
    private  String subCode;

    @Id
    private String section;

    private String primaryTeacherId;

    private  String substituteTeacherId;

    private String lastUpdateTime;

}
