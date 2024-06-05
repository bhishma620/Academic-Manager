package com.bhishma.ams.entity;

import com.bhishma.ams.config.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Faculty_Data")
public class Faculty {

    @Id
    private String mailId;
    private String name;
    private String shortName;
    private String password;
}
