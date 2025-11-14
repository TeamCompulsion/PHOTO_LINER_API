package kr.kro.photoliner.domain.photo.service;

import kr.kro.photoliner.domain.photo.dto.request.MapMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.response.MapMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.model.Photos;
import kr.kro.photoliner.domain.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final GeometryFactory geometryFactory;

    public PhotosResponse getPhotoList(Long userId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Photos photos = photoRepository.findPhotosByUserId(userId, pageable);
        return PhotosResponse.from(photos);
    }

    public MapMarkersResponse getMarkersInViewport(MapMarkersRequest request) {
        Point sw = geometryFactory.createPoint(request.getSouthWestCoordinate());
        Point ne = geometryFactory.createPoint(request.getNorthEastCoordinate());

        Photos photos = photoRepository.findPhotosByUserIdInBox(request.userId(), sw, ne);

        return MapMarkersResponse.of(
                photos.filterInDate(request.from(), request.to()),
                photos.filterOutOfDate(request.from(), request.to())
        );
    }
}
