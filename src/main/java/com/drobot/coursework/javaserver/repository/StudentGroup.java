package com.drobot.coursework.javaserver.repository;

import com.drobot.coursework.javaserver.repository.common.NameWithActiveEntity;
import com.drobot.coursework.javaserver.repository.common.TuitionForm;
import com.drobot.coursework.javaserver.repository.common.TuitionTerm;
import jakarta.persistence.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class StudentGroup extends NameWithActiveEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Specialization specialization;
    private int creationYear;
    @Enumerated(value = EnumType.STRING)
    private TuitionForm tuitionForm = TuitionForm.FULL_TIME;
    @Enumerated(value = EnumType.STRING)
    private TuitionTerm tuitionTerm = TuitionTerm.REGULAR;
    private int studySemesters;
    private BigDecimal studyYears;
    private int realBeginYear;
    private int beginYears;
    @OneToMany(mappedBy = "studentGroup", fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.ALL)
    @Where(clause = "active = true")
    private List<StudentDegree> studentDegrees = new ArrayList<>();
//
//    public List<StudentDegree> getStudentDegrees() {
//        studentDegrees.sort(new StudentDegreeFullNameComparator());
//        return studentDegrees;
//    }

    public List<Student> getActiveStudents() {
        if (studentDegrees.isEmpty()) {
            return new ArrayList<>();
        } else {
            return getStudentDegrees().stream().map(StudentDegree::getStudent).collect(Collectors.toList());
        }
    }


    public int getCurrentCourse() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        // Якщо зараз вересень або пізніше — вже новий навчальний рік
        boolean isAfterSeptember = currentMonth >= 9;

        return currentYear - creationYear + (isAfterSeptember ? 1 : 0);
    }

}
