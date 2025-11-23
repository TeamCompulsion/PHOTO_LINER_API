package kr.kro.photoliner.domain.album.controller;

import jakarta.validation.Valid;
import kr.kro.photoliner.domain.album.dto.request.AlbumCreateRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumDeleteRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumItemCreateRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumItemDeleteRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumPhotoMarkersRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumTitleUpdateRequest;
import kr.kro.photoliner.domain.album.dto.response.AlbumCreateResponse;
import kr.kro.photoliner.domain.album.dto.response.AlbumPhotoItemsResponse;
import kr.kro.photoliner.domain.album.dto.response.AlbumPhotoMarkersResponse;
import kr.kro.photoliner.domain.album.dto.response.AlbumsResponse;
import kr.kro.photoliner.domain.album.service.AlbumService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumCreateResponse> createAlbum(
            @Valid @RequestBody AlbumCreateRequest request,
            @Auth Long userId
    ) {
        AlbumCreateResponse response = albumService.createAlbum(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<AlbumsResponse> getAlbums(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @Auth Long userId
    ) {
        return ResponseEntity.ok(albumService.getAlbums(userId, pageable));
    }

    @PatchMapping("/{albumId}/title")
    public ResponseEntity<Void> updateAlbumTitle(
            @PathVariable Long albumId,
            @RequestBody @Valid AlbumTitleUpdateRequest request,
            @Auth Long userId
    ) {
        albumService.updateAlbumTitle(albumId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePhoto(
            @Valid @RequestBody AlbumDeleteRequest request,
            @Auth Long userId
    ) {
        albumService.deleteAlbums(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{albumId}/photos")
    public ResponseEntity<AlbumPhotoItemsResponse> getAlbumItems(
            @PathVariable Long albumId,
            @PageableDefault Pageable pageable,
            @Auth Long userId
    ) {
        return ResponseEntity.ok(albumService.getAlbumPhotoItems(albumId, pageable));
    }

    @PostMapping("/{albumId}/photos")
    public ResponseEntity<Void> createAlbumItems(
            @PathVariable Long albumId,
            @RequestBody @Valid AlbumItemCreateRequest request,
            @Auth Long userId
    ) {
        albumService.createAlbumItems(albumId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{albumId}/photos")
    public ResponseEntity<Void> deleteAlbumItems(
            @PathVariable Long albumId,
            @RequestBody @Valid AlbumItemDeleteRequest request,
            @Auth Long userId
    ) {
        albumService.deleteAlbumItems(albumId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{albumId}/markers")
    public ResponseEntity<AlbumPhotoMarkersResponse> getAlbumPhotoMarkers(
            @PathVariable Long albumId,
            @Valid AlbumPhotoMarkersRequest request,
            @Auth Long userId
    ) {
        return ResponseEntity.ok(albumService.getAlbumPhotoMarkers(albumId, request));
    }
}
