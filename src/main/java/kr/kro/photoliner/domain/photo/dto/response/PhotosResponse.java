package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.domain.photo.model.Photo;

import java.time.LocalDateTime;
import java.util.List;

public record PhotosResponse(
        Integer count,
        List<InnerPhotoResponse> photos
) {
    public static PhotosResponse from(List<Photo> photos) {
        return new PhotosResponse(
                photos.size(),
                photos.stream()
                        .map(InnerPhotoResponse::from)
                        .toList()
        );
    }

    public record InnerPhotoResponse(
            Long id,
            String filePath,
            LocalDateTime capturedDt,
            Long userId
    ) {
        public static InnerPhotoResponse from(Photo photo) {
            return new InnerPhotoResponse(
                    photo.getId(),
                    photo.getFilePath(),
                    photo.getCapturedDt(),
                    photo.getUser().getId());
        }
    }
}
