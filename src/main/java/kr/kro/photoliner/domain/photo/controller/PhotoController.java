package kr.kro.photoliner.domain.photo.controller;

import kr.kro.photoliner.domain.photo.dto.request.ViewportMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.dto.response.ViewportMarkersResponse;
import kr.kro.photoliner.domain.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/photo")
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/list")
    public ResponseEntity<PhotosResponse> getPhotoList(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(photoService.getPhotoList(userId));
    }

    @GetMapping("/viewport")
    public ResponseEntity<ViewportMarkersResponse> getViewport(ViewportMarkersRequest request) {
        return ResponseEntity.ok(photoService.getMarkersInViewport(request));
    }
}
