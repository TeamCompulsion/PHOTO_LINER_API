package kr.kro.photoliner.domain.photo.dto.response;

import java.util.List;

public record MarkersResponse(
        Integer count,
        List<MarkerResponse> markers
) {
}
