package com.drobot.coursework.javaserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@AllArgsConstructor
public class DocumentResponseController {


//    private final com.drobot.coursework.javaserver.service.DocumentResponseController documentResponseController;
    protected static final String MEDIA_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    protected static final String MEDIA_TYPE_PDF = "application/pdf";

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