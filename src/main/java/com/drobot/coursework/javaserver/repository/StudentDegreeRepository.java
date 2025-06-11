package com.drobot.coursework.javaserver.repository;

import com.drobot.coursework.javaserver.entity.StudentDegree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDegreeRepository extends JpaRepository<StudentDegree, Long> {
}
