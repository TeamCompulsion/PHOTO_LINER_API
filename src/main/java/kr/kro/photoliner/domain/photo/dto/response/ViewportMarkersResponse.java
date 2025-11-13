package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photos;

import java.time.LocalDate;

public record ViewportMarkersResponse(
        PhotoMarkersResponse photoMarkersResponse,
        MarkersResponse markersResponse
) {
    public static ViewportMarkersResponse from(Photos photos, LocalDate from, LocalDate to) {
        return new ViewportMarkersResponse(
                photos.getPhotoMarkersInDateRange(from, to),
                photos.getMarkersOutOfDateRange(from, to)
        );
    }
}
