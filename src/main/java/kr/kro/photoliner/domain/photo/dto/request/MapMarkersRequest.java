package kr.kro.photoliner.domain.photo.dto.request;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record MapMarkersRequest(
        Long userId,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDate from,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDate to,
        double swLat,
        double swLng,
        double neLat,
        double neLng
) {

    public MapMarkersRequest {
        validateUserId();
        validateDate(from);
        validateDate(to);
        validateLatitude(swLat);
        validateLatitude(neLat);
        validateLongitude(swLng);
        validateLongitude(neLng);
    }

    private void validateUserId() {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateLatitude(double lat) {
        if (lat < 0 || lat > 90) {
            throw new IllegalArgumentException();
        }
    }

    private void validateLongitude(double lng) {
        if (lng < 0 || lng > 180) {
            throw new IllegalArgumentException();
        }
    }
}
