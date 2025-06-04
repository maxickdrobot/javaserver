package com.drobot.coursework.javaserver.repository.common;

import com.drobot.coursework.javaserver.repository.Course;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseCourse extends BaseEntity {

    @ManyToOne
    protected Course course;

}
