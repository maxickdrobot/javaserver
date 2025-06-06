package com.drobot.coursework.javaserver.controller;

import com.drobot.coursework.javaserver.service.CourseForStudent;
import com.drobot.coursework.javaserver.service.DocumentService;
import com.drobot.coursework.javaserver.service.FileFormatEnum;
import com.drobot.coursework.javaserver.service.DocumentResponseService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "api/")
@AllArgsConstructor
public class DocumentResponseController {

    private final DocumentResponseService studentsService;
    private final DocumentService examReportService;
//    private final com.drobot.coursework.javaserver.service.DocumentResponseController documentResponseController;
    protected static final String MEDIA_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    protected static final String MEDIA_TYPE_PDF = "application/pdf";

    @GetMapping("/academic-difference/docx")
    public ResponseEntity<Resource> generateForGroup(@RequestParam List<Long> studentGroupId) throws Exception {
        List<CourseForStudent> examReportDataBeans = studentsService.getStudents(studentGroupId);
        System.out.println("[DEBUG] Кількість студентів: " + examReportDataBeans.size());
        for (CourseForStudent student : examReportDataBeans) {
            System.out.println("[DEBUG] Студент: " + student.getStudents());
        }

        File examReport = examReportService.generateReports(examReportDataBeans, FileFormatEnum.DOCX);
        return buildDocumentResponseEntity(examReport, examReport.getName(), MEDIA_TYPE_DOCX);
    }



    protected static ResponseEntity buildDocumentResponseEntity(File result, String asciiName, String mediaType) {
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(result));
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + asciiName)
                    .header("content-filename", asciiName)
                    .header("Access-Control-Expose-Headers", "content-filename")
                    .contentType(MediaType.parseMediaType(mediaType))
                    .contentLength(result.length())
                    .body(resource);
        } catch (FileNotFoundException exception) {
            return handleException(exception);
        }
    }

    private static ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Помилка під час створення документа: " + exception.getMessage());
    }
}