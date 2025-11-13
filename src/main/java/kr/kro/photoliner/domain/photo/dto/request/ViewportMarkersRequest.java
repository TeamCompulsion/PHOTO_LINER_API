package kr.kro.photoliner.domain.photo.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ViewportMarkersRequest(
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
}
