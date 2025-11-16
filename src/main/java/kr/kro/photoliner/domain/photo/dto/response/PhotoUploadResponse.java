package kr.kro.photoliner.domain.photo.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record PhotoUploadResponse(
        int totalUploaded,
        List<UploadedPhotoInfo> uploadedPhotos
) {

    public record UploadedPhotoInfo(
            Long photoId,
            String fileName,
            String filePath,
            LocalDateTime capturedDt,
            Double latitude,
            Double longitude
    ) {

    }
}
