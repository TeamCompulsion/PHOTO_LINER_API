package kr.kro.photoliner.domain.photo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
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
        validateFile(file);
        String fileName = generateFileName(file);
        saveFile(file, fileName);
        return fileName;
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.contains("..")) {
            throw new IllegalArgumentException("Invalid file path: " + originalFilename);
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        return UUID.randomUUID() + "." + extension;
    }

    private void saveFile(MultipartFile file, String fileName) {
        try {
            Path targetLocation = uploadLocation.resolve(fileName);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to store file: " + file.getOriginalFilename(), e);
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
