package kr.kro.photoliner.domain.photo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.kro.photoliner.domain.photo.model.Photo;
import org.locationtech.jts.geom.Point;

public record PhotoUploadResponse(
        int totalUploaded,
        List<InnerUploadedPhotoInfo> uploadedPhotos
) {

    public record InnerUploadedPhotoInfo(
            Long photoId,
            String fileName,
            String filePath,
            LocalDateTime capturedDt,
            Double latitude,
            Double longitude
    ) {
        public static InnerUploadedPhotoInfo from(Photo photo) {
            return new InnerUploadedPhotoInfo(
                    photo.getId(),
                    photo.getFileName(),
                    photo.getFilePath(),
                    photo.getCapturedDt(),
                    Optional.ofNullable(photo.getLocation())
                            .map(Point::getY)
                            .orElse(null),
                    Optional.ofNullable(photo.getLocation())
                            .map(Point::getX)
                            .orElse(null)
            );
        }
    }

    public static PhotoUploadResponse from(List<InnerUploadedPhotoInfo> uploadedPhotos) {
        return new PhotoUploadResponse(uploadedPhotos.size(), uploadedPhotos);
    }
}
