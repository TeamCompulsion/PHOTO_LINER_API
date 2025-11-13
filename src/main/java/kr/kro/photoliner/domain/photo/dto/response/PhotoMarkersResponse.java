package kr.kro.photoliner.domain.photo.dto.response;

import java.util.List;

public record PhotoMarkersResponse(
        Integer count,
        List<PhotoMarkerResponse> photoMarkers
) {
}
