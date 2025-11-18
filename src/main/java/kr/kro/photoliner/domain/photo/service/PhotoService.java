package kr.kro.photoliner.domain.photo.service;

import kr.kro.photoliner.domain.photo.dto.request.MapMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoCapturedDateUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoLocationUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.response.MapMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.model.Photos;
import kr.kro.photoliner.domain.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final GeometryFactory geometryFactory;

    @Transactional(readOnly = true)
    public PhotosResponse getPhotoList(Long userId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Photos photos = photoRepository.findPhotosByUserId(userId, pageable);
        return PhotosResponse.from(photos);
    }

    @Transactional(readOnly = true)
    public MapMarkersResponse getMarkersInViewport(MapMarkersRequest request) {
        Point sw = geometryFactory.createPoint(request.getSouthWestCoordinate());
        Point ne = geometryFactory.createPoint(request.getNorthEastCoordinate());

        Photos photos = photoRepository.findPhotosByUserIdInBox(request.userId(), sw, ne);

        return MapMarkersResponse.of(
                photos.filterInDate(request.from(), request.to()),
                photos.filterOutOfDate(request.from(), request.to())
        );
    }

    @Transactional
    public void updatePhotoCapturedDate(Long photoId, PhotoCapturedDateUpdateRequest request) {
        Photo photo = photoRepository.getById(photoId);
        photo.updateCapturedDate(request.capturedDt());
    }

    @Transactional
    public void updatePhotoLocation(Long photoId, PhotoLocationUpdateRequest request) {
        Photo photo = photoRepository.getById(photoId);
        Point location = geometryFactory.createPoint(
                new Coordinate(request.longitude(), request.latitude())
        );
        photo.updateLocation(location);
    }
}
