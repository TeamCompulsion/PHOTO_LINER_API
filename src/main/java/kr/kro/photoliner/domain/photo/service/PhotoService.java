package kr.kro.photoliner.domain.photo.service;

import kr.kro.photoliner.domain.photo.dto.DeletePhotosRequest;
import kr.kro.photoliner.domain.photo.dto.request.MapMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoCapturedDateUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoLocationUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.response.MapMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.model.AlbumPhotos;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.model.Photos;
import kr.kro.photoliner.domain.photo.repository.AlbumPhotoRepository;
import kr.kro.photoliner.domain.photo.repository.PhotoRepository;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final AlbumPhotoRepository albumPhotoRepository;
    private final PhotoRepository photoRepository;
    private final GeometryFactory geometryFactory;

    @Transactional(readOnly = true)
    public PhotosResponse getPhotos(Long userId, Pageable pageable) {
        return PhotosResponse.from(photoRepository.findByUserId(userId, pageable));
    }

    @Transactional(readOnly = true)
    public PhotosResponse getPhotosByAlbumId(Long userId, Long albumId, Pageable pageable) {
        return PhotosResponse.from(photoRepository.findByUserIdAndAlbumId(userId, albumId, pageable));
    }

    @Transactional(readOnly = true)
    public MapMarkersResponse getMarkersInViewport(MapMarkersRequest request) {
        Point sw = geometryFactory.createPoint(request.getSouthWestCoordinate());
        Point ne = geometryFactory.createPoint(request.getNorthEastCoordinate());

        Photos photos = photoRepository.getPhotosByUserIdInBox(request.userId(), sw, ne);
        AlbumPhotos albumPhotos = albumPhotoRepository.getByPhotoIdIn(photos.getPhotoIds());

        return MapMarkersResponse.of(
                albumPhotos.getPhotoIncludedAlbum(request.albumId()),
                albumPhotos.getPhotoNotIncludedAlbum(request.albumId())
        );
    }

    @Transactional
    public void updatePhotoCapturedDate(Long photoId, PhotoCapturedDateUpdateRequest request) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_PHOTO, "photo id: " + photoId));
        photo.updateCapturedDate(request.capturedDt());
    }

    @Transactional
    public void updatePhotoLocation(Long photoId, PhotoLocationUpdateRequest request) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_PHOTO, "photo id: " + photoId));
        Point location = geometryFactory.createPoint(
                new Coordinate(request.longitude(), request.latitude())
        );
        photo.updateLocation(location);
    }

    @Transactional
    public void deletePhotos(DeletePhotosRequest request) {
        photoRepository.deleteAllByIdInBatch(request.ids());
    }
}
