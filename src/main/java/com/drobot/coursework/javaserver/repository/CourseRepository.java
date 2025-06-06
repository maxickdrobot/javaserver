package com.drobot.coursework.javaserver.repository;

import com.drobot.coursework.javaserver.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
