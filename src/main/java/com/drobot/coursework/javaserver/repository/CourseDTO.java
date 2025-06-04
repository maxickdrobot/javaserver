package com.drobot.coursework.javaserver.repository;

import com.drobot.coursework.javaserver.repository.common.NameWithEngEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class CourseDTO {
    private String name;
    private int credits;
    private int hours;
    private int semester;
    private String control;
    private String traditionalGrade;
    private Integer grade; // nullable

}
