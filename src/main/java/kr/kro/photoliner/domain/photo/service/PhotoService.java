package kr.kro.photoliner.domain.photo.service;

import kr.kro.photoliner.domain.photo.dto.request.ViewportMarkersRequest;
import kr.kro.photoliner.domain.photo.dto.response.PhotosResponse;
import kr.kro.photoliner.domain.photo.dto.response.ViewportMarkersResponse;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final GeometryFactory geometryFactory;

    public PhotosResponse getPhotoList(Long userId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        List<Photo> photos = photoRepository.findByUserId(userId, pageable);
        return PhotosResponse.from(photos);
    }

    public ViewportMarkersResponse getMarkersInViewport(ViewportMarkersRequest request) {
        Point sw = geometryFactory.createPoint(new Coordinate(request.swLng(), request.swLat()));
        Point ne = geometryFactory.createPoint(new Coordinate(request.neLng(), request.neLat()));

        List<Photo> photos = photoRepository.findByUserIdInBox(request.userId(), sw, ne);

        return ViewportMarkersResponse.from(
                new Photos(photos),
                request.from(),
                request.to()
        );
    }
}
