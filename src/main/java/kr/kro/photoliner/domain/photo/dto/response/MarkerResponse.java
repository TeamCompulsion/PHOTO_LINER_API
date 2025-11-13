package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MarkerResponse {
    private final Long id;
    private final LocalDateTime capturedDt;
    private final double lat;
    private final double lng;

    public static MarkerResponse from(Photo photo) {
        return new MarkerResponse(
                photo.getId(),
                photo.getCapturedDt(),
                photo.getLocation().getY(),
                photo.getLocation().getX()
        );
    }
}