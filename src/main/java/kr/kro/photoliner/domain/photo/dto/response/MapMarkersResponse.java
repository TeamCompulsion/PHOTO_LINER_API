package kr.kro.photoliner.domain.photo.dto.response;

public record MapMarkersResponse(
        PhotoMarkersResponse photoMarkersResponse,
        PoiMarkersResponse poiMarkersResponse
) {
}
