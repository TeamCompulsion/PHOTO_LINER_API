package kr.kro.photoliner.domain.photo.dto;

import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

public record ExifData(
        LocalDateTime capturedDt,
        Point location
) {

}
