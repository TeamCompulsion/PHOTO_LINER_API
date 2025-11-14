package kr.kro.photoliner.domain.photo.controller;

import jakarta.validation.Valid;
import kr.kro.photoliner.domain.photo.dto.request.MapMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.response.MapMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/photos")
public class PhotoController {

    private final PhotoService photoService;

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
}
