package kr.kro.photoliner.domain.photo.service;

import java.util.List;
import java.util.Optional;
import kr.kro.photoliner.domain.photo.dto.ExifData;
import kr.kro.photoliner.domain.photo.dto.response.PhotoUploadResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotoUploadResponse.UploadedPhotoInfo;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.repository.PhotoRepository;
import kr.kro.photoliner.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PhotoUploadService {

    private final FileStorageService fileStorageService;
    private final ExifExtractorService exifExtractorService;
    private final PhotoRepository photoRepository;

    @Transactional
    public PhotoUploadResponse uploadPhotos(Long userId, List<MultipartFile> files) {
        List<UploadedPhotoInfo> uploadedPhotos = files.stream()
                .map(file -> {
                    ExifData exifData = exifExtractorService.extractExifData(file);
                    String filePath = fileStorageService.store(file);
                    User user = User.builder()
                            .id(userId)
                            .build();
                    Photo photo = Photo.builder()
                            .fileName(file.getOriginalFilename())
                            .filePath(filePath)
                            .capturedDt(exifData.capturedDt())
                            .location(exifData.location())
                            .user(user)
                            .build();
                    Photo savedPhoto = photoRepository.save(photo);
                    return new UploadedPhotoInfo(
                            savedPhoto.getId(),
                            savedPhoto.getFileName(),
                            savedPhoto.getFilePath(),
                            savedPhoto.getCapturedDt(),
                            Optional.ofNullable(savedPhoto.getLocation())
                                    .map(Point::getY)
                                    .orElse(null),
                            Optional.ofNullable(savedPhoto.getLocation())
                                    .map(Point::getX)
                                    .orElse(null)
                    );
                })
                .toList();
        return new PhotoUploadResponse(
                uploadedPhotos.size(),
                uploadedPhotos
        );
    }

    @Transactional(readOnly = true)
    public Photo getPhoto(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalStateException("Photo not found with id: " + photoId));
    }

    public Resource loadAsResource(String filePath) {
        return fileStorageService.loadAsResource(filePath);
    }

    public String determineMimeType(String filePath) {
        String extension = getExtension(filePath);
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "heic", "heif" -> "image/heic";
            default -> "application/octet-stream";
        };
    }

    private String getExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }
}
