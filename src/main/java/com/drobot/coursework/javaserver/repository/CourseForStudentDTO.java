package com.drobot.coursework.javaserver.repository;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseForStudentDTO {
    private int course;
    private String degree;
    private String tuitionForm;
    private int codeSpecialization;
    private String nameSpecialization;
    private String nameSpeciality;
    private List<StudentDTO> students;
}
