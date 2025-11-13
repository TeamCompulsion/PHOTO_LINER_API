package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PhotoMarkerResponse extends MarkerResponse {
    private final String filePath;

    public PhotoMarkerResponse(Long id, LocalDateTime capturedDt, String filePath, double lat, double lng) {
        super(id, capturedDt, lat, lng);
        this.filePath = filePath;
    }

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
