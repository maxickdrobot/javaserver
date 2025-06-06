package com.drobot.coursework.javaserver.service;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tr;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class DocumentService extends DocumentIOService {

    public File generateReports(List<CourseForStudent> courseForStudents, FileFormatEnum format)
            throws Docx4JException, FileNotFoundException {
        if (courseForStudents == null || courseForStudents.isEmpty()) {
            throw new IllegalArgumentException("CourseForStudents list is empty or null");
        }

        WordprocessingMLPackage template = loadTemplate(TEMPLATES_PATH + "template.docx");

        Tbl table = TemplateUtil.findTable(template, "з/п");
        if (table == null) {
            throw new Docx4JException("Table with placeholder #з/п not found");
        }

        List<Tr> rows = TemplateUtil.getAllRowsFromTable(table);
        if (rows.isEmpty()) {
            throw new Docx4JException("No rows found in the table");
        }
        Tr templateRow = rows.get(rows.size() - 1);

        P yearParagraph = TemplateUtil.findParagraphWithPlaceholder(template, "StudentYear");
        P nameParagraph = TemplateUtil.findParagraphWithPlaceholder(template, "StudentName");
        if (yearParagraph == null || nameParagraph == null) {
            throw new Docx4JException("Required placeholder paragraphs not found");
        }

        template.getMainDocumentPart().getContent().remove(yearParagraph);
        template.getMainDocumentPart().getContent().remove(nameParagraph);

        template.getMainDocumentPart().getContent().remove(table);

        boolean first = true;
        for (CourseForStudent cfs : courseForStudents) {
            if (!first) {
                TemplateUtil.createLineBreak();
            }

            P newYearParagraph = (P) XmlUtils.deepCopy(yearParagraph);
            template.getMainDocumentPart().getContent().add(newYearParagraph);
            TemplateUtil.replaceTextPlaceholdersInElement(newYearParagraph, Map.of(
                    "StudentYear", String.valueOf(cfs.getStudentYear()),
                    "Degree", cfs.getDegree(),
                    "TuitionForm", cfs.getTuitionForm(),
                    "codeSpecialization", cfs.getSpecialityCode(),
                    "nameSpecialization", cfs.getSpecializationName(),
                    "nameSpeciality", cfs.getSpecialityName()
            ), false);

            for (Student student : cfs.getStudents()) {
                String key = student.getFullName() + "_" + cfs.getStudentYear() + "_" + cfs.getSpecialityCode();
                List<Course> courses = student.getCourses();

                P newNameParagraph = (P) XmlUtils.deepCopy(nameParagraph);
                template.getMainDocumentPart().getContent().add(newNameParagraph);
                TemplateUtil.replaceTextPlaceholdersInElement(newNameParagraph,
                        Map.of("StudentName", student.getFullName()), false);

                if (!courses.isEmpty()) {
                    Tbl newTable = (Tbl) XmlUtils.deepCopy(table);
                    template.getMainDocumentPart().getContent().add(newTable);

                    List<Tr> tableRows = TemplateUtil.getAllRowsFromTable(newTable);
                    Tr currentTemplateRow = tableRows.get(tableRows.size() - 1);
                    int rowNumber = tableRows.size() - 1;

                    for (int i = 0; i < courses.size(); i++) {
                        Course course = courses.get(i);
                        TemplateUtil.addRowToTable(newTable, currentTemplateRow, rowNumber++, Map.of(
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

                    newTable.getContent().remove(currentTemplateRow);
                }
            }

            first = false;
        }

        String fileName = "ExamReport_";
        return saveDocumentToTemp(template, fileName, format);
    }
}