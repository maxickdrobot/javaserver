package com.drobot.coursework.javaserver.repository;

import com.drobot.coursework.javaserver.entity.CourseForStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseForStudentRepository extends JpaRepository<CourseForStudent, Long> {
}
