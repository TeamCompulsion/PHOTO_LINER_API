package kr.kro.photoliner.domain.photo.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.locationtech.jts.geom.Coordinate;

public record PhotoMarkersRequest(
        @Min(0) @Max(90)
        double swLat,

        @Min(0) @Max(180)
        double swLng,

        @Min(0) @Max(90)
        double neLat,

        @Min(0) @Max(180)
        double neLng
) {
    public Coordinate getSouthWestCoordinate() {
        return new Coordinate(swLng, swLat);
    }

    public Coordinate getNorthEastCoordinate() {
        return new Coordinate(neLng, neLat);
    }
}
