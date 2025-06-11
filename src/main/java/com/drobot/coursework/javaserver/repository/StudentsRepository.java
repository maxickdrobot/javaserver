package com.drobot.coursework.javaserver.repository;

import com.drobot.coursework.javaserver.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository extends JpaRepository<Student, Long> {
}
