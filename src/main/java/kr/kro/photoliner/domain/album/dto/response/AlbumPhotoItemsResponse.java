package kr.kro.photoliner.domain.album.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.kro.photoliner.domain.album.dto.AlbumPhotoItem;

public record AlbumPhotoItemsResponse(
        List<InnerAlbumPhotoItem> items
) {

    public static AlbumPhotoItemsResponse from(List<AlbumPhotoItem> albumPhotoItems) {
        return new AlbumPhotoItemsResponse(
                albumPhotoItems.stream()
                        .map(InnerAlbumPhotoItem::from)
                        .toList()
        );
    }

    public record InnerAlbumPhotoItem(
            Long id,
            Long photoId,
            String fileName,
            String filePath,
            String thumbnailPath,
            LocalDateTime capturedDt,
            Double latitude,
            Double longitude
    ) {

        public static InnerAlbumPhotoItem from(AlbumPhotoItem albumPhotoItem) {
            return new InnerAlbumPhotoItem(
                    albumPhotoItem.id(),
                    albumPhotoItem.photoId(),
                    albumPhotoItem.fileName(),
                    albumPhotoItem.filePath(),
                    albumPhotoItem.thumbnailPath(),
                    albumPhotoItem.capturedDt(),
                    albumPhotoItem.getLatitude(),
                    albumPhotoItem.getLongitude()
            );
        }
    }
}
