package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photos;

import java.time.LocalDate;

public record MapMarkersResponse(
        PhotoMarkersResponse photoMarkersResponse,
        PoiMarkersResponse poiMarkersResponse
) {
    public static MapMarkersResponse from(Photos photos, LocalDate from, LocalDate to) {
        return new MapMarkersResponse(
                photos.getPhotoMarkersInDateRange(from, to),
                photos.getMarkersOutOfDateRange(from, to)
        );
    }
}
