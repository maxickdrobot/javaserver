package com.drobot.coursework.javaserver.entity;

import com.drobot.coursework.javaserver.entity.common.NameWithEngAndActiveEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Speciality extends NameWithEngAndActiveEntity {
    private String code;
    private String fieldOfStudyCode;
    private String fieldOfStudy;
    private String fieldOfStudyEng;
    private String nameGenitive;
//    @ManyToOne
//    private FieldOfKnowledge fieldOfKnowledge;
    private String nameInternational;
    private String regulatedProfessionAccess;
    private String regulatedProfessionAccessEng;
    private String entranceCertificates;
    private String entranceCertificatesEng;
}
