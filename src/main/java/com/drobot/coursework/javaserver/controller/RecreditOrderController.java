package com.drobot.coursework.javaserver.controller;

import com.drobot.coursework.javaserver.service.CourseForStudent;
import com.drobot.coursework.javaserver.service.DocumentResponseService;
import com.drobot.coursework.javaserver.service.RecreditOrderService;
import com.drobot.coursework.javaserver.service.FileFormatEnum;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping(path = "api/")
@AllArgsConstructor
public class RecreditOrderController extends DocumentResponseController{

    private final DocumentResponseService documentResponseService;
    private final RecreditOrderService recreditOrderService;

    @GetMapping("/recredit-order/docx")
    public ResponseEntity<Resource> generateForGroup(@RequestParam List<Long> studentGroupIds) throws Exception {
        List<CourseForStudent> recreditOrderDataBeans = documentResponseService.getStudents(studentGroupIds);
        File recreditOrder = recreditOrderService.generateOrders(recreditOrderDataBeans, FileFormatEnum.DOCX);
        return buildDocumentResponseEntity(recreditOrder, recreditOrder.getName(), MEDIA_TYPE_DOCX);
    }

}
