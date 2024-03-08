package com.excel.in.controller;

import com.excel.in.exceptions.DuplicateEntryException;
import com.excel.in.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file){
          return ResponseEntity.ok(fileService.uploadFile(file));
    }
}

