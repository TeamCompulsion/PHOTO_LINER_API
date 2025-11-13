package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photo;

import java.time.LocalDateTime;

public record MarkerResponse(
        Long id,
        LocalDateTime capturedDt,
        double lat,
        double lng
) {
    public static MarkerResponse from(Photo photo) {
        return new MarkerResponse(
                photo.getId(),
                photo.getCapturedDt(),
                photo.getLocation().getY(),
                photo.getLocation().getX()
        );
    }
}
