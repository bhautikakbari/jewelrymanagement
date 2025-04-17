package com.jwelkam.jewelrymanagement.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, String directory) throws IOException;
    void deleteFile(String filePath) throws IOException;
}
