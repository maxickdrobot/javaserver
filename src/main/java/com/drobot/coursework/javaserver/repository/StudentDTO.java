package com.drobot.coursework.javaserver.repository;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDTO {
    private String fullName;
    private List<CourseDTO> courses;
}
