package com.drobot.coursework.javaserver.service;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseForStudent {
    private int studentYear;
    private String degree;
    private String tuitionForm;
    private String specializationName;
    private String specialityName;
    private String specialityCode;
    private List<Student> students;
}
