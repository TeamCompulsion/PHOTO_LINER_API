package kr.kro.photoliner.domain.photo.controller;

import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.dto.response.ViewportResponse;
import kr.kro.photoliner.domain.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/photo")
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/list")
    public ResponseEntity<PhotosResponse> getPhotoList(
            @RequestParam Long userId
            ){
        return ResponseEntity.ok(photoService.getPhotoList(userId));
    }

    @GetMapping("/viewport")
    public ResponseEntity<ViewportResponse> getViewport(
            @RequestParam Long userId,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to,
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng
            ){
        return ResponseEntity.ok(photoService.getViewport(userId, from, to, swLat, swLng, neLat, neLng));
    }
}
