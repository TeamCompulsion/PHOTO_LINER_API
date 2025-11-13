package kr.kro.photoliner.domain.photo.dto.response;

import java.util.List;

public record PoiMarkersResponse(
        Integer count,
        List<PoiMarkerResponse> markers
) {
}
