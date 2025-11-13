package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photo;

import java.time.LocalDate;
import java.util.List;

public class Photos {
    private final List<Photo> photos;

    public Photos(List<Photo> photos) {
        this.photos = photos;
    }

    public MarkersResponse getMarkersOutOfDateRange(LocalDate from, LocalDate to) {
        List<MarkerResponse> markers = photos.stream()
                .filter(photo -> photo.isBetween(from, to))
                .map(MarkerResponse::from)
                .toList();
        return new MarkersResponse(
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
