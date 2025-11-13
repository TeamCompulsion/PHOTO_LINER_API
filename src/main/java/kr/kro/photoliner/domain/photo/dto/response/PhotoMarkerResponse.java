package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photo;

import java.time.LocalDateTime;

public record PhotoMarkerResponse(
        Long id,
        LocalDateTime capturedDt,
        String filePath,
        double lat,
        double lng
) {

    public static PhotoMarkerResponse from(Photo photo) {
        return new PhotoMarkerResponse(
                photo.getId(),
                photo.getCapturedDt(),
                photo.getFilePath(),
                photo.getLocation().getY(),
                photo.getLocation().getX()
        );
    }
}
