package com.drobot.coursework.javaserver.entity;


import com.drobot.coursework.javaserver.entity.common.BaseCourse;
import com.drobot.coursework.javaserver.entity.common.CourseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "courses_for_students")
public class CourseForStudent extends BaseCourse {
    @ManyToOne
    private StudentDegree studentDegree;
    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    private CourseType courseType;
    private boolean selective;

    public CourseForStudent() {}

    public CourseForStudent(Course course, StudentDegree studentDegree, CourseType courseType) {
        this.course = course;
        this.studentDegree = studentDegree;
        this.courseType = courseType;
    }

    public CourseForStudent(Course course, StudentDegree studentDegree, CourseType courseType, boolean selective) {
        this.course = course;
        this.studentDegree = studentDegree;
        this.courseType = courseType;
        this.selective = selective;
    }

}
