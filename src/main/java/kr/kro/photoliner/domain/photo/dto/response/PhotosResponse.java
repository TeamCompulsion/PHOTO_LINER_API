package kr.kro.photoliner.domain.photo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.model.Photos;
import org.locationtech.jts.geom.Point;

public record PhotosResponse(
        Integer count,
        List<InnerPhotoResponse> photos
) {

    public static PhotosResponse from(Photos photos) {
        return new PhotosResponse(
                photos.count(),
                photos.photos().stream()
                        .map(InnerPhotoResponse::from)
                        .toList()
        );
    }

    public record InnerPhotoResponse(
            Long id,
            String filePath,
            LocalDateTime capturedDt,
            Double lat,
            Double lng,
            Long userId
    ) {

        public static InnerPhotoResponse from(Photo photo) {
            Optional<Point> location = Optional.ofNullable(photo.getLocation());
            return new InnerPhotoResponse(
                    photo.getId(),
                    photo.getFilePath(),
                    photo.getCapturedDt(),
                    location.map(Point::getY).orElse(null),
                    location.map(Point::getX).orElse(null),
                    photo.getUser().getId());
        }
    }
}
