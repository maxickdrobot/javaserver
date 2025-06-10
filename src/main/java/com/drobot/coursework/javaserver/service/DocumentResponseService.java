package com.drobot.coursework.javaserver.service;

import com.drobot.coursework.javaserver.entity.StudentDegree;
import com.drobot.coursework.javaserver.entity.common.CourseType;
import com.drobot.coursework.javaserver.repository.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter
public class DocumentResponseService {
    private final StudentsRepository studentsRepository;
    private final CourseRepository courseRepository;
    private final CourseForStudentRepository courseForStudentRepository;

    record GroupKey(int studentYear, String degreeName, String tuitionForm, String specialityCode, String specializationName, String specialityName) {}

    public List<CourseForStudent> getStudents(List<Long> studentGroupIds) {
        List<com.drobot.coursework.javaserver.entity.CourseForStudent> filteredCourseForStudents =
                courseForStudentRepository.findAll().stream()
                        .filter(record ->
                                studentGroupIds.contains(record.getStudentDegree().getStudentGroup().getId()) &&
                                        record.getCourseType() == CourseType.RECREDIT
                        )
                        .toList();

        List<CourseForStudent> result = new ArrayList<>();
        Map<GroupKey, List<com.drobot.coursework.javaserver.entity.CourseForStudent>> grouped = filteredCourseForStudents.stream().collect(Collectors.groupingBy(record -> {
            StudentDegree degree = record.getStudentDegree();
            return new GroupKey(
                    degree.getStudentGroup().getCurrentCourse(),
                    degree.getSpecialization().getDegree().getName(),
                    degree.getTuitionForm().getNameUkr(),
                    degree.getSpecialization().getSpeciality().getCode(),
                    degree.getSpecialization().getName(),
                    degree.getSpecialization().getSpeciality().getName()
            );
        }));

        for (var entry : grouped.entrySet()) {
            GroupKey key = entry.getKey();
            List<com.drobot.coursework.javaserver.entity.CourseForStudent> courseForStudents = entry.getValue();

            var groupedByStudent = courseForStudents.stream().collect(Collectors.groupingBy(r -> r.getStudentDegree().getStudent()));

            List<Student> studentDTOs = groupedByStudent.entrySet().stream().map(studentEntry -> {
                com.drobot.coursework.javaserver.entity.Student student = studentEntry.getKey();
                List<com.drobot.coursework.javaserver.entity.CourseForStudent> studentCourses = studentEntry.getValue();

                List<Course> courseDTOs = studentCourses.stream().map(record -> {
                    com.drobot.coursework.javaserver.entity.Course course = record.getCourse();
                    return Course.builder()
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

                return Student.builder()
                        .fullName(student.getFullNameUkr())
                        .courses(courseDTOs)
                        .build();
            }).collect(Collectors.toList());

            CourseForStudent dto = CourseForStudent.builder()
                    .studentYear(key.studentYear())
                    .degree(key.degreeName())
                    .tuitionForm(key.tuitionForm())
                    .specialityCode(key.specialityCode())
                    .specializationName(key.specializationName())
                    .specialityName(key.specialityName())
                    .students(studentDTOs)
                    .build();

            result.add(dto);
        }
        System.out.println("Result: " + result);
        return result;
    }
}
