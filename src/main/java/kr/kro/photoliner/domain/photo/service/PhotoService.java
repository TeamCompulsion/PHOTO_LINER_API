package kr.kro.photoliner.domain.photo.service;

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

import java.time.LocalDate;
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

    public ViewportMarkersResponse getMarkersInViewport(
            Long userId,
            LocalDate from,
            LocalDate to,
            double swLat,
            double swLng,
            double neLat,
            double neLng
    ) {
        Point sw = geometryFactory.createPoint(new Coordinate(swLng, swLat));
        Point ne = geometryFactory.createPoint(new Coordinate(neLng, neLat));
        List<Photo> photos = photoRepository.findByUserIdInBox(userId, sw, ne);

        return ViewportMarkersResponse.from(
                new Photos(photos),
                from,
                to
        );
    }
}
