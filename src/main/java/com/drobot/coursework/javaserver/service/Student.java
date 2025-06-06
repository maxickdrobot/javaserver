package com.drobot.coursework.javaserver.service;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Student {
    private String fullName;
    private List<Course> courses;
}
