package com.drobot.coursework.javaserver.service;

import com.drobot.coursework.javaserver.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentsService {
    private final StudentsRepository studentsRepository;
    private final CourseRepository courseRepository;
    private final CourseForStudentRepository courseForStudentRepository;

    record GroupKey(int course, String degreeName, String tuitionForm, int specializationCode, String specializationName, String specialityName) {}

    public List<CourseForStudentDTO> getStudents() {
        List<CourseForStudent> data = courseForStudentRepository.findAll();
        List<CourseForStudentDTO> result = new ArrayList<>();

        var grouped = data.stream().collect(Collectors.groupingBy(record -> {
            StudentDegree degree = record.getStudentDegree();
            return new GroupKey(
                    degree.getStudentGroup().getCurrentCourse(),
                    degree.getSpecialization().getDegree().getName(),
                    degree.getTuitionForm().getNameUkr(),
                    Integer.parseInt(degree.getSpecialization().getCode()),
                    degree.getSpecialization().getName(),
                    degree.getSpecialization().getSpeciality().getName()
            );
        }));

        for (var entry : grouped.entrySet()) {
            GroupKey key = entry.getKey();
            List<CourseForStudent> records = entry.getValue();

            var groupedByStudent = records.stream().collect(Collectors.groupingBy(r -> r.getStudentDegree().getStudent()));

            List<StudentDTO> studentDTOs = groupedByStudent.entrySet().stream().map(studentEntry -> {
                Student student = studentEntry.getKey();
                List<CourseForStudent> studentCourses = studentEntry.getValue();

                List<CourseDTO> courseDTOs = studentCourses.stream().map(record -> {
                    Course course = record.getCourse();
                    return CourseDTO.builder()
                            .name(course.getCourseName().getName())
                            .credits(course.getCredits().intValue())
                            .hours(course.getHours())
                            .semester(course.getSemester())
                            .control(course.getKnowledgeControl() != null ? course.getKnowledgeControl().getName() : "Невизначено")
                            .traditionalGrade(
                                    course.getGrades().stream()
                                            .filter(g -> g.getCourse().getId() == course.getId())
                                            .findFirst()
                                            .map(g -> g.getNationalGradeUkr() != null ? g.getNationalGradeUkr() : "Невідомо")
                                            .orElse("Заборгованість")
                            )
                            .grade(
                                    course.getGrades().stream()
                                            .filter(g -> g.getCourse().getId() == course.getId())
                                            .findFirst()
                                            .map(g -> g.getPoints() != null ? g.getPoints() : null)
                                            .orElse(null)
                            )
                            .build();
                }).collect(Collectors.toList());

                return StudentDTO.builder()
                        .fullName(student.getFullNameUkr())
                        .courses(courseDTOs)
                        .build();
            }).collect(Collectors.toList());

            CourseForStudentDTO dto = CourseForStudentDTO.builder()
                    .course(key.course())
                    .degree(key.degreeName())
                    .tuitionForm(key.tuitionForm())
                    .codeSpecialization(key.specializationCode())
                    .nameSpecialization(key.specializationName())
                    .nameSpeciality(key.specialityName())
                    .students(studentDTOs)
                    .build();

            result.add(dto);
        }

        return result;
    }
}
