package kr.kro.photoliner.domain.photo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.kro.photoliner.domain.photo.model.Photo;
import org.springframework.data.domain.Page;

public record PhotosResponse(
        List<InnerPhotoResponse> photos,
        InnerPageInfo pageInfo
) {

    public static PhotosResponse from(Page<Photo> photoPage) {
        return new PhotosResponse(
                photoPage.getContent().stream()
                        .map(InnerPhotoResponse::from)
                        .toList(),
                InnerPageInfo.from(photoPage)
        );
    }

    public record InnerPageInfo(
            long totalElements,
            int totalPages,
            int currentPage,
            int size,
            boolean hasNext,
            boolean hasPrevious
    ) {

        public static InnerPageInfo from(Page<Photo> page) {
            return new InnerPageInfo(
                    page.getTotalElements(),
                    page.getTotalPages(),
                    page.getNumber(),
                    page.getSize(),
                    page.hasNext(),
                    page.hasPrevious()
            );
        }
    }

    public record InnerPhotoResponse(
            Long id,
            String filePath,
            String thumbnailPath,
            LocalDateTime capturedDt,
            Double lat,
            Double lng,
            Long userId
    ) {

        public static InnerPhotoResponse from(Photo photo) {
            return new InnerPhotoResponse(
                    photo.getId(),
                    photo.getFilePath(),
                    photo.getThumbnailPath(),
                    photo.getCapturedDt(),
                    photo.getLongitude(),
                    photo.getLatitude(),
                    photo.getId());
        }
    }
}
