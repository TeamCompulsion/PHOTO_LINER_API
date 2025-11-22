package kr.kro.photoliner.domain.photo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.model.Photos;

public record PhotoMarkersResponse(
        Integer count,
        List<InnerPhotoMarker> photoMarkers
) {

    public static PhotoMarkersResponse from(Photos albumPhotoItems) {
        return new PhotoMarkersResponse(
                albumPhotoItems.count(),
                albumPhotoItems.photos().stream()
                        .map(InnerPhotoMarker::from)
                        .toList()
        );
    }

    public record InnerPhotoMarker(
            Long id,
            String filePath,
            String thumbnailPath,
            LocalDateTime capturedDt,
            Double lat,
            Double lng
    ) {

        public static InnerPhotoMarker from(Photo photo) {
            return new InnerPhotoMarker(
                    photo.getId(),
                    photo.getFilePath(),
                    photo.getThumbnailPath(),
                    photo.getCapturedDt(),
                    photo.getLongitude(),
                    photo.getLatitude()
            );
        }
    }
}
