package kr.kro.photoliner.domain.photo.service;

import java.util.List;
import kr.kro.photoliner.domain.album.model.Album;
import kr.kro.photoliner.domain.album.repository.AlbumRepository;
import kr.kro.photoliner.domain.photo.dto.DeletePhotosRequest;
import kr.kro.photoliner.domain.photo.dto.request.MapMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoCapturedDateUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.request.PhotoLocationUpdateRequest;
import kr.kro.photoliner.domain.photo.dto.response.MapMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.infra.FileStorage;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.model.Photos;
import kr.kro.photoliner.domain.photo.repository.PhotoRepository;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final GeometryFactory geometryFactory;
    private final FileStorage fileStorage;

    @Transactional(readOnly = true)
    public PhotosResponse getPhotosByIds(Long userId, Pageable pageable) {
        return PhotosResponse.from(photoRepository.findByUserId(userId, pageable));
    }

    @Transactional(readOnly = true)
    public MapMarkersResponse getMarkersInViewport(MapMarkersRequest request) {
        Point sw = geometryFactory.createPoint(request.getSouthWestCoordinate());
        Point ne = geometryFactory.createPoint(request.getNorthEastCoordinate());

        Photos photos = photoRepository.getPhotosByUserIdInBox(request.userId(), sw, ne);
        Album album = albumRepository.findById(request.albumId())
                .orElseThrow(
                        () -> CustomException.of(ApiResponseCode.NOT_FOUND_ALBUM, "album id: " + request.albumId()));

        return MapMarkersResponse.of(
                photos.filterInAlbum(album),
                photos.filterOutOfAlbum(album)
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
        List<Photo> photos = photoRepository.findAllById(request.ids());
        photos.forEach(photo -> fileStorage.deleteOriginalImage(photo.getFilePath()));
        photos.forEach(photo -> fileStorage.deleteThumbnailImage(photo.getFilePath()));
        photoRepository.deleteAllByIdInBatch(request.ids());
    }

    @Transactional(readOnly = true)
    public PhotosResponse getPhotosByIds(List<Long> ids, Pageable pageable) {
        Page<Photo> photos = photoRepository.findByIdIn(ids, pageable);
        return PhotosResponse.from(photos);
    }
}
