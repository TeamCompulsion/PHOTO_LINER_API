package kr.kro.photoliner.domain.photo.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.locationtech.jts.geom.Coordinate;

public record CreatePhotosRequest(
        @NotNull
        Long userId,

        @NotNull
        @NotEmpty
        List<InnerPhoto> photos
) {

    public record InnerPhoto(
            @NotNull
            String fileName,

            @NotNull
            String uploadFileName,

            @Nullable
            LocalDateTime capturedDate,

            @Nullable
            Integer latitude,

            @Nullable
            Integer longitude
    ) {

        public Coordinate convertToGeo() {
            if (Objects.isNull(latitude) || Objects.isNull(longitude)) {
                return null;
            }
            return new Coordinate(latitude, longitude);
        }
    }
}
