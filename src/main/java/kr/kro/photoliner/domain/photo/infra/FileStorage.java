package kr.kro.photoliner.domain.photo.infra;

import static kr.kro.photoliner.global.code.ApiResponseCode.DIRECTORY_CREATION_FAILED;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorage {

    private final Path originalLocation;
    private final Path thumbnailLocation;

    private static final String ORIGINAL_DIR = "original";
    private static final String THUMBNAIL_DIR = "thumbnail";
    private static final String BASE_IMAGES_DIR = "/images";

    private static final int THUMBNAIL_WIDTH = 300;
    private static final int THUMBNAIL_HEIGHT = 300;

    public FileStorage(@Value("${photo.upload.base-dir}") String baseDir) {
        Path rootLocation = Paths.get(baseDir).toAbsolutePath().normalize();
        this.originalLocation = rootLocation.resolve(ORIGINAL_DIR);
        this.thumbnailLocation = rootLocation.resolve(THUMBNAIL_DIR);

        try {
            Files.createDirectories(this.originalLocation);
            Files.createDirectories(this.thumbnailLocation);
        } catch (IOException e) {
            throw CustomException.of(DIRECTORY_CREATION_FAILED);
        }
    }

    public String store(MultipartFile file) {
        validateFile(file);
        String fileName = generateFileName(file);
        saveFile(file, this.originalLocation, fileName);
        return BASE_IMAGES_DIR + "/" + ORIGINAL_DIR + "/" + fileName;
    }

    public String storeThumbnail(String originalRelativePath) {
        String fileName = Paths.get(originalRelativePath).getFileName().toString();
        Path sourceLocation = this.originalLocation.resolve(fileName);
        Path targetLocation = this.thumbnailLocation.resolve(fileName);

        try {
            Thumbnails.of(sourceLocation.toFile())
                    .size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                    .toFile(targetLocation.toFile());
            return BASE_IMAGES_DIR + "/" + THUMBNAIL_DIR + "/" + fileName;
        } catch (IOException e) {
            throw CustomException.of(ApiResponseCode.FILE_CREATION_FAILED);
        }
    }

    public void deleteOriginalImage(String originalPath) {
        String fileName = Paths.get(originalPath).getFileName().toString();
        Path sourceLocation = this.originalLocation.resolve(fileName);

        try {
            Files.delete(sourceLocation);
        } catch (IOException e) {
            throw CustomException.of(ApiResponseCode.FILE_DELETE_FAILED);
        }
    }

    public void deleteThumbnailImage(String thumbnailPath) {
        String fileName = Paths.get(thumbnailPath).getFileName().toString();
        Path sourceLocation = this.thumbnailLocation.resolve(fileName);

        try {
            Files.delete(sourceLocation);
        } catch (IOException e) {
            throw CustomException.of(ApiResponseCode.FILE_DELETE_FAILED);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw CustomException.of(ApiResponseCode.INVALID_FILE);
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.contains("..")) {
            throw CustomException.of(ApiResponseCode.INVALID_FILE_NAME, "file title: " + originalFilename);
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        return UUID.randomUUID() + "." + extension;
    }

    private void saveFile(MultipartFile file, Path directory, String fileName) {
        try {
            Path targetLocation = directory.resolve(fileName);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw CustomException.of(ApiResponseCode.FILE_STORE_ERROR, "file title: " + file.getOriginalFilename(), e);
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
