package com.excel.in.controller;

import com.excel.in.exceptions.FileWriteException;
import com.excel.in.model.Employee;
import com.excel.in.utils.DynamicExcelColumnNamesExporter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/")
@AllArgsConstructor
public class DynamicExcelColumnNamesExporterController {
    private final DynamicExcelColumnNamesExporter dynamicExcelColumnNamesExporter;
    @PostMapping("/DynamicExcelColumnNamesExporter")
    public ResponseEntity<String> DynamicExcelColumnNamesExporter() throws FileWriteException {
        return ResponseEntity.ok(dynamicExcelColumnNamesExporter.exportColumnNames(Employee.class));
    }
}
