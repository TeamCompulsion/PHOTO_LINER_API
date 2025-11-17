package kr.kro.photoliner.domain.photo.service;

import java.util.List;
import kr.kro.photoliner.domain.photo.dto.ExifData;
import kr.kro.photoliner.domain.photo.dto.response.PhotoUploadResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotoUploadResponse.InnerUploadedPhotoInfo;
import kr.kro.photoliner.domain.photo.infra.ExifExtractor;
import kr.kro.photoliner.domain.photo.infra.FileStorage;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.repository.PhotoRepository;
import kr.kro.photoliner.domain.user.model.User;
import kr.kro.photoliner.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PhotoUploadService {

    private final FileStorage fileStorage;
    private final ExifExtractor exifExtractor;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Transactional
    public PhotoUploadResponse uploadPhotos(Long userId, List<MultipartFile> files) {
        List<InnerUploadedPhotoInfo> uploadedPhotos = files.stream()
                .map(file -> {
                    ExifData exifData = exifExtractor.extract(file);
                    String filePath = fileStorage.store(file);
                    String fileName = file.getOriginalFilename();
                    User user = userRepository.findUserById(userId)
                            .orElseThrow(RuntimeException::new);
                    Photo photo = createPhoto(user, exifData, filePath, fileName);
                    Photo savedPhoto = photoRepository.save(photo);
                    return InnerUploadedPhotoInfo.from(savedPhoto);
                })
                .toList();
        return PhotoUploadResponse.from(uploadedPhotos);
    }

    private Photo createPhoto(User user, ExifData exifData, String fileName, String filePath) {
        return Photo.builder()
                .fileName(fileName)
                .filePath(filePath)
                .capturedDt(exifData.capturedDt())
                .location(exifData.location())
                .user(user)
                .build();
    }
}
