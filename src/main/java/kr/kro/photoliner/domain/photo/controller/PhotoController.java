package kr.kro.photoliner.domain.photo.controller;

import jakarta.validation.Valid;
import java.util.List;
import kr.kro.photoliner.domain.photo.dto.request.MapMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.response.MapMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotoUploadResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.service.PhotoService;
import kr.kro.photoliner.domain.photo.service.PhotoUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoUploadService photoUploadService;

    @GetMapping
    public ResponseEntity<PhotosResponse> getPhotoList(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(photoService.getPhotoList(userId));
    }

    @GetMapping("/markers")
    public ResponseEntity<MapMarkersResponse> getMarkersInViewport(@Valid MapMarkersRequest request) {
        return ResponseEntity.ok(photoService.getMarkersInViewport(request));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoUploadResponse> uploadPhotos(
            @RequestParam("userId") Long userId,
            @RequestPart("files") List<MultipartFile> files
    ) {
        PhotoUploadResponse response = photoUploadService.uploadPhotos(userId, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{photoId}/image")
    public ResponseEntity<Resource> downloadImage(
            @PathVariable Long photoId
    ) {
        Photo photo = photoUploadService.getPhoto(photoId);
        Resource resource = photoUploadService.loadAsResource(photo.getFilePath());
        String mimeType = photoUploadService.determineMimeType(photo.getFilePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + photo.getFileName() + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=3600")
                .body(resource);
    }
}
