package kr.kro.photoliner.domain.album.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import org.locationtech.jts.geom.Point;

public record AlbumPhotoItem(
        Long id,
        Long photoId,
        String fileName,
        String filePath,
        String thumbnailPath,
        LocalDateTime capturedDt,
        Point location
) {

    public Double getLatitude() {
        if (Objects.isNull(location)) {
            return null;
        }
        return location.getY();
    }

    public Double getLongitude() {
        if (Objects.isNull(location)) {
            return null;
        }
        return location.getX();
    }
}
