package com.drobot.coursework.javaserver.controller;


import com.drobot.coursework.javaserver.repository.Course;
import com.drobot.coursework.javaserver.repository.CourseForStudent;
import com.drobot.coursework.javaserver.repository.CourseForStudentDTO;
import com.drobot.coursework.javaserver.repository.Student;
import com.drobot.coursework.javaserver.service.StudentsService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/students")
public class StudentsController {

    private final StudentsService studentsService;

    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping
    public List<CourseForStudentDTO> getStudents() {
        return studentsService.getStudents();
    }

}
