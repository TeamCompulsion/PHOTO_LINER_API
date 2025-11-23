package kr.kro.photoliner.domain.photo.service;

import java.util.List;
import kr.kro.photoliner.domain.photo.dto.request.CreatePhotosRequest;
import kr.kro.photoliner.domain.photo.dto.request.DeletePhotosRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoCapturedDateUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoLocationUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.response.PhotoMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.infra.S3CustomClient;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.model.Photos;
import kr.kro.photoliner.domain.photo.repository.PhotoRepository;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final GeometryFactory geometryFactory;
    private final S3CustomClient s3CustomClient;

    @Value("${cloud.aws.cdn.base-url}")
    private String cdnURL;

    private static final String ORIGINAL_BASE_PATH = "/images/original/";
    private static final String THUMBNAIL_BASE_PATH = "/images/thumb/";

    @Transactional(readOnly = true)
    public PhotosResponse getPhotosByIds(Long userId, Boolean hasLocation, Boolean hasCapturedDate, Pageable pageable) {
        return PhotosResponse.from(
                photoRepository.findByUserIdWithFilters(userId, hasLocation, hasCapturedDate, pageable));
    }

    @Transactional(readOnly = true)
    public PhotoMarkersResponse getPhotoMarkers(Long userId, PhotoMarkersRequest request) {
        Point sw = geometryFactory.createPoint(request.getSouthWestCoordinate());
        Point ne = geometryFactory.createPoint(request.getNorthEastCoordinate());

        Photos photos = photoRepository.getByUserIdInBox(userId, sw, ne);

        return PhotoMarkersResponse.from(photos);
    }

    @Transactional
    public void createPhotos(CreatePhotosRequest request) {
        List<Photo> photos = request.photos().stream()
                .map(photo -> Photo.builder()
                        .userId(request.userId())
                        .fileName(photo.fileName())
                        .filePath(cdnURL + ORIGINAL_BASE_PATH + photo.uploadFileName())
                        .thumbnailPath(cdnURL + THUMBNAIL_BASE_PATH + photo.uploadFileName())
                        .capturedDt(photo.capturedDate())
                        .location(getPointOrNull(photo.convertToGeo()))
                        .build()
                ).toList();
        photoRepository.saveAll(photos);
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
        List<Photo> photos = photoRepository.findAllById(request.ids());
        photos.forEach(photo -> s3CustomClient.delete(photo.getFilePath()));
        photos.forEach(photo -> s3CustomClient.delete(photo.getThumbnailPath()));
        photoRepository.deleteAllByIdInBatch(request.ids());
    }

    private Point getPointOrNull(Coordinate coordinate) {
        if (coordinate == null) {
            return null;
        }
        return geometryFactory.createPoint(coordinate);
    }
}
