package com.drobot.coursework.javaserver.entity;

import com.drobot.coursework.javaserver.entity.common.Person;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Student extends Person {
    private String surnameEng;
    private String nameEng;
    private String patronimicEng;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String registrationAddress;
    private String actualAddress;
    private String school;
    private String telephone;
    private String email;
//    @ManyToOne
//    private Privilege privilege;
    private String fatherName;
    private String fatherPhone;
    private String fatherInfo;
    private String motherName;
    private String motherPhone;
    private String motherInfo;
    private String notes;
    private String photoUrl;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<StudentDegree> degrees = new HashSet<>();
}
