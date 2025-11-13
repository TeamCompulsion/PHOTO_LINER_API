package kr.kro.photoliner.domain.photo.dto.response;

import java.time.LocalDate;

public record ViewportResponse(
        PhotoMarkersResponse photoMarkersResponse,
        MarkersResponse markersResponse
) {
    public static ViewportResponse from(Photos photos, LocalDate from, LocalDate to) {
        return new ViewportResponse(
                photos.getPhotoMarkersInDateRange(from, to),
                photos.getMarkersOutOfDateRange(from, to)
        );
    }
}
