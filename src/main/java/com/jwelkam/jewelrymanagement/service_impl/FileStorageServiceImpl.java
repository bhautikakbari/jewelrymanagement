package com.jwelkam.jewelrymanagement.service_impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import com.jwelkam.jewelrymanagement.service.FileStorageService;

import org.springframework.util.StringUtils;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file, String directory) throws IOException {

        if (file.isEmpty()) {
            throw new BadRequestException("Failed to store empty file");
        }

        Path uploadPath = Paths.get(uploadDir, directory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String fileName = UUID.randomUUID().toString() + fileExtension;

        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return directory + "/" + fileName;
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        if (filePath != null && !filePath.isEmpty()) {
            Path path = Paths.get(uploadDir, filePath);
            Files.deleteIfExists(path);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

}
