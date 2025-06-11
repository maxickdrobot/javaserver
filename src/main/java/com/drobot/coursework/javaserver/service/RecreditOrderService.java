package com.drobot.coursework.javaserver.service;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Br;
import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tr;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class RecreditOrderService extends DocumentIOService {

    public File generateOrders(List<CourseForStudent> courseForStudents, FileFormatEnum format)
            throws Docx4JException, FileNotFoundException {
        if (courseForStudents == null || courseForStudents.isEmpty()) {
            throw new IllegalArgumentException("CourseForStudents list is empty or null");
        }

        WordprocessingMLPackage template = loadTemplate(TEMPLATES_PATH + "template.docx");

        Tbl courseTableTemplate = TemplateUtil.findTable(template, "з/п");
        if (courseTableTemplate == null) {
            throw new Docx4JException("Table with placeholder #з/п not found");
        }

        List<Tr> tableRowsTemplate = TemplateUtil.getAllRowsFromTable(courseTableTemplate);
        if (tableRowsTemplate.isEmpty()) {
            throw new Docx4JException("No rows found in the table");
        }

        P groupInfoParagraphTemplate = TemplateUtil.findParagraphWithPlaceholder(template, "StudentYear");
        P studentNameParagraphTemplate = TemplateUtil.findParagraphWithPlaceholder(template, "StudentName");
        if (groupInfoParagraphTemplate == null || studentNameParagraphTemplate == null) {
            throw new Docx4JException("Required placeholder paragraphs not found");
        }

        template.getMainDocumentPart().getContent().remove(groupInfoParagraphTemplate);
        template.getMainDocumentPart().getContent().remove(studentNameParagraphTemplate);
        template.getMainDocumentPart().getContent().remove(courseTableTemplate);

        boolean first = true;
        for (CourseForStudent courseForStudent : courseForStudents) {
            if (!first) {
                P breakParagraph = new P();
                Br lineBreak = TemplateUtil.createLineBreak();
                breakParagraph.getContent().add(lineBreak);
                template.getMainDocumentPart().getContent().add(breakParagraph);
            }


            P newGroupInfoParagraph = (P) XmlUtils.deepCopy(groupInfoParagraphTemplate);
            template.getMainDocumentPart().getContent().add(newGroupInfoParagraph);
            TemplateUtil.replaceTextPlaceholdersInElement(newGroupInfoParagraph, Map.of(
                    "StudentYear", String.valueOf(courseForStudent.getStudentYear()),
                    "Degree", courseForStudent.getDegree().toLowerCase(),
                    "TuitionForm", EducationFormUtil.toGenitive(courseForStudent.getTuitionForm()),
                    "codeSpecialization", courseForStudent.getSpecialityCode(),
                    "nameSpecialization", courseForStudent.getSpecializationName(),
                    "nameSpeciality", courseForStudent.getSpecialityName()
            ), false);

            for (Student student : courseForStudent.getStudents()) {
                P breakParagraph = new P();
                Br lineBreak = TemplateUtil.createLineBreak();
                breakParagraph.getContent().add(lineBreak);
                template.getMainDocumentPart().getContent().add(breakParagraph);
                String key = student.getFullName() + "_" + courseForStudent.getStudentYear() + "_" + courseForStudent.getSpecialityCode();
                List<Course> courses = student.getCourses();

                P newNameParagraph = (P) XmlUtils.deepCopy(studentNameParagraphTemplate);
                template.getMainDocumentPart().getContent().add(newNameParagraph);
                TemplateUtil.replaceTextPlaceholdersInElement(newNameParagraph,
                        Map.of("StudentName", student.getFullName()), false);

                if (!courses.isEmpty()) {
                    Tbl newStudentCourseTable = (Tbl) XmlUtils.deepCopy(courseTableTemplate);
                    template.getMainDocumentPart().getContent().add(newStudentCourseTable);

                    List<Tr> studentTableRows = TemplateUtil.getAllRowsFromTable(newStudentCourseTable);
                    Tr currentTemplateRow = studentTableRows.get(studentTableRows.size() - 1);
                    int rowNumber = studentTableRows.size() - 1;

                    for (int i = 0; i < courses.size(); i++) {
                        Course course = courses.get(i);
                        TemplateUtil.addRowToTable(newStudentCourseTable, currentTemplateRow, rowNumber++, Map.of(
                                "N", String.valueOf(i + 1),
                                "CourseName", course.getName(),
                                "Cr", String.valueOf(course.getCredits()),
                                "H", String.valueOf(course.getHours()),
                                "S", String.valueOf(course.getSemester()),
                                "Kr", course.getControl(),
                                "TrGr", course.getTraditionalGrade(),
                                "Gr", course.getGrade() != null ? String.valueOf(course.getGrade()) : ""
                        ));
                    }

                    newStudentCourseTable.getContent().remove(currentTemplateRow);
                }
            }

            first = false;
        }

        String fileName = "ExamReport_";
        return saveDocumentToTemp(template, fileName, format);
    }
}