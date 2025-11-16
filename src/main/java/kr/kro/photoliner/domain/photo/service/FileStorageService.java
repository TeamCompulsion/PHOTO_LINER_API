package kr.kro.photoliner.domain.photo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path uploadLocation;

    public FileStorageService(@Value("${photo.upload.base-dir}") String baseDir) {
        this.uploadLocation = Paths.get(baseDir)
                .toAbsolutePath()
                .normalize();
        try {
            Files.createDirectories(this.uploadLocation);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create upload directory", e);
        }
    }

    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.contains("..")) {
            throw new IllegalArgumentException("Invalid file path: " + originalFilename);
        }

        try {
            String fileName = UUID.randomUUID() + "." + getExtension(originalFilename);
            Path targetLocation = uploadLocation.resolve(fileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
            return fileName;

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to store file: " + originalFilename, e);
        }
    }

    public Resource loadAsResource(String fileName) {
        try {
            Path file = uploadLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load file: " + fileName, e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path file = uploadLocation.resolve(fileName).normalize();
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to delete file: " + fileName, e);
        }
    }

    private String getExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }
}
