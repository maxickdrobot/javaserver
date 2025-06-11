package com.drobot.coursework.javaserver.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Course {
    private String name;
    private int credits;
    private int hours;
    private int semester;
    private String control;
    private String traditionalGrade;
    private Integer grade;
}
