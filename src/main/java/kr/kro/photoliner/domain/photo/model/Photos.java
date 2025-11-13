package kr.kro.photoliner.domain.photo.model;

import kr.kro.photoliner.domain.photo.dto.response.PhotoMarkerResponse;
import kr.kro.photoliner.domain.photo.dto.response.PhotoMarkersResponse;
import kr.kro.photoliner.domain.photo.dto.response.PoiMarkerResponse;
import kr.kro.photoliner.domain.photo.dto.response.PoiMarkersResponse;

import java.time.LocalDate;
import java.util.List;

public class Photos {
    private final List<Photo> photos;

    public Photos(List<Photo> photos) {
        this.photos = photos;
    }

    public PoiMarkersResponse getMarkersOutOfDateRange(LocalDate from, LocalDate to) {
        List<PoiMarkerResponse> markers = photos.stream()
                .filter(photo -> photo.isBetween(from, to))
                .map(PoiMarkerResponse::from)
                .toList();
        return new PoiMarkersResponse(
                markers.size(),
                markers
        );
    }

    public PhotoMarkersResponse getPhotoMarkersInDateRange(LocalDate from, LocalDate to) {
        List<PhotoMarkerResponse> photoMarkers = photos.stream()
                .filter(photo -> photo.isBetween(from, to))
                .map(PhotoMarkerResponse::from)
                .toList();
        return new PhotoMarkersResponse(
                photoMarkers.size(),
                photoMarkers
        );
    }
}
