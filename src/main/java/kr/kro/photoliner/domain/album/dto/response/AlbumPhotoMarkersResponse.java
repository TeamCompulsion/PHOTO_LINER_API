package kr.kro.photoliner.domain.album.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.kro.photoliner.domain.album.dto.AlbumPhotoItem;
import kr.kro.photoliner.domain.album.dto.AlbumPhotoItems;

public record AlbumPhotoMarkersResponse(
        Integer count,
        List<InnerAlbumPhotoMarker> albumPhotoMarkers
) {

    public static AlbumPhotoMarkersResponse from(AlbumPhotoItems albumPhotoItems) {
        return new AlbumPhotoMarkersResponse(
                albumPhotoItems.count(),
                albumPhotoItems.photoItems().stream()
                        .map(InnerAlbumPhotoMarker::from)
                        .toList()
        );
    }

    public record InnerAlbumPhotoMarker(
            Long id,
            Long photoId,
            String filePath,
            String thumbnailPath,
            LocalDateTime capturedDt,
            Double lat,
            Double lng
    ) {

        public static InnerAlbumPhotoMarker from(AlbumPhotoItem albumPhotoItem) {
            return new InnerAlbumPhotoMarker(
                    albumPhotoItem.id(),
                    albumPhotoItem.photoId(),
                    albumPhotoItem.filePath(),
                    albumPhotoItem.thumbnailPath(),
                    albumPhotoItem.capturedDt(),
                    albumPhotoItem.getLatitude(),
                    albumPhotoItem.getLongitude()
            );
        }
    }
}
