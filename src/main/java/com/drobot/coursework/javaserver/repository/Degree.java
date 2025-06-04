package com.drobot.coursework.javaserver.repository;


import com.drobot.coursework.javaserver.repository.common.NameWithEngEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Degree extends NameWithEngEntity {
    @Column(name="qualification_level_descr")
    private String qualificationLevelDescription;
    @Column(name="qualification_level_descr_eng")
    private String qualificationLevelDescriptionEng;
    @Column(length = 300)
    private String admissionRequirements;
    @Column(length = 255)
    private String admissionRequirementsEng;
    @Column(length = 255)
    private String admissionForeignRequirements;
    @Column(length = 255)
    private String admissionForeignRequirementsEng;
    @Column(length = 255)
    private String furtherStudyAccess;
    @Column(length = 255)
    private String furtherStudyAccessEng;

    public Degree() {
    }

    public Degree(int id, String name){
        setId(id);
        setName(name);
    }

    public Degree(String name, String nameEng) {
        super(name, nameEng);
    }
}
