package com.drobot.coursework.javaserver.entity;

import com.drobot.coursework.javaserver.entity.common.NameWithEngEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourseName extends NameWithEngEntity {
    private String abbreviation;

    public CourseName(String courseName, String courseNameEng) {
        super(courseName, courseNameEng);
    }
}
