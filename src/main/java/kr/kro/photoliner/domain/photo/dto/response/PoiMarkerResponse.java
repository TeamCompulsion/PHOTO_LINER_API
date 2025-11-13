package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photo;

import java.time.LocalDateTime;

public record PoiMarkerResponse(
        Long id,
        LocalDateTime capturedDt,
        double lat,
        double lng
) {
    public static PoiMarkerResponse from(Photo photo) {
        return new PoiMarkerResponse(
                photo.getId(),
                photo.getCapturedDt(),
                photo.getLocation().getY(),
                photo.getLocation().getX()
        );
    }
}
