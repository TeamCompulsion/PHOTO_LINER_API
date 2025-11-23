package kr.kro.photoliner.domain.photo.controller;

import jakarta.validation.Valid;
import java.util.List;
import kr.kro.photoliner.domain.photo.dto.request.CreatePhotosRequest;
import kr.kro.photoliner.domain.photo.dto.request.DeletePhotosRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoCapturedDateUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoLocationUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.request.PresignedUrlRequest;
import kr.kro.photoliner.domain.photo.dto.response.PhotoMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.dto.response.PresignedUrlResponse;
import kr.kro.photoliner.domain.photo.infra.S3CustomClient;
import kr.kro.photoliner.domain.photo.service.PhotoService;
import kr.kro.photoliner.global.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final S3CustomClient s3CustomClient;

    @GetMapping
    public ResponseEntity<PhotosResponse> getPhotos(
            @Auth Long userId,
            @RequestParam(required = false) Boolean hasLocation,
            @RequestParam(required = false) Boolean hasCapturedDate,
            @PageableDefault(sort = "capturedDt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(photoService.getPhotosByIds(userId, hasLocation, hasCapturedDate, pageable));
    }

    @GetMapping("/markers")
    public ResponseEntity<PhotoMarkersResponse> getPhotoMarkers(
            @Valid PhotoMarkersRequest request,
            @Auth Long userId
    ) {
        return ResponseEntity.ok(photoService.getPhotoMarkers(userId, request));
    }

    @PostMapping("/presigned-urls")
    public ResponseEntity<List<PresignedUrlResponse>> getPresignedUrls(
            @Valid @RequestBody List<PresignedUrlRequest> requests
    ) {
        List<PresignedUrlResponse> responses = s3CustomClient.generatePresignedUrls(requests);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Void> createPhotos(
            @Valid @RequestBody CreatePhotosRequest request,
            @Auth Long userId
    ) {
        photoService.createPhotos(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{photoId}/captured-date")
    public ResponseEntity<Void> updatePhotoCapturedDate(
            @Auth Long userId,
            @PathVariable Long photoId,
            @Valid @RequestBody PhotoCapturedDateUpdateRequest request
    ) {
        photoService.updatePhotoCapturedDate(photoId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{photoId}/location")
    public ResponseEntity<Void> updatePhotoLocation(
            @PathVariable Long photoId,
            @Valid @RequestBody PhotoLocationUpdateRequest request,
            @Auth Long userId
    ) {
        photoService.updatePhotoLocation(photoId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePhoto(
            @Valid @RequestBody DeletePhotosRequest request,
            @Auth Long userId
    ) {
        photoService.deletePhotos(request);
        return ResponseEntity.noContent().build();
    }
}
