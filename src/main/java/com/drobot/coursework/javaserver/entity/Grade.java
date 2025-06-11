package com.drobot.coursework.javaserver.entity;

import com.drobot.coursework.javaserver.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Grade extends BaseEntity {
    @ManyToOne
    private Course course;
    @ManyToOne
    private StudentDegree studentDegree;
    private Integer grade;
    private Integer points;
    private Boolean onTime;
    @Column(name = "ects", length = 2)
    @Enumerated(value = EnumType.STRING)
    private EctsGrade ects;
    private boolean academicDifference;

    public Grade(Course course, StudentDegree studentDegree, int points) {
        this.course = course;
        this.studentDegree = studentDegree;
        this.points = points;
        this.onTime = true;
        this.grade = EctsGrade.getGrade(points, course.getKnowledgeControl().isGraded());
        this.ects = EctsGrade.getEctsGrade(points);
    }

    public String getNationalGradeUkr() {
        if (ects == null) {
            return "";
        }
        return ects.getNationalGradeUkr(this);
    }

    public String getNationalGradeEng() {
        if (ects == null) {
            return "";
        }
        return ects.getNationalGradeEng(this);
    }
}
