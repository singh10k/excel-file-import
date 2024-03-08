package com.excel.in.service;

import com.excel.in.exceptions.DuplicateEntryException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);
}
