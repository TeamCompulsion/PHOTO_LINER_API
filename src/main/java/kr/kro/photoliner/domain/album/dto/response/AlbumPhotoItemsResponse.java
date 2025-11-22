package kr.kro.photoliner.domain.album.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.kro.photoliner.domain.album.model.view.AlbumPhotoView;
import org.springframework.data.domain.Page;

public record AlbumPhotoItemsResponse(
        List<InnerAlbumPhotoItem> items
) {

    public static AlbumPhotoItemsResponse from(Page<AlbumPhotoView> albumPhotoViews) {
        return new AlbumPhotoItemsResponse(
                albumPhotoViews.stream()
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
            LocalDateTime capturedDt
    ) {

        public static InnerAlbumPhotoItem from(AlbumPhotoView albumPhotoView) {
            return new InnerAlbumPhotoItem(
                    albumPhotoView.getId(),
                    albumPhotoView.getPhotoId(),
                    albumPhotoView.getFileName(),
                    albumPhotoView.getFilePath(),
                    albumPhotoView.getThumbnailPath(),
                    albumPhotoView.getCapturedDt()
            );
        }
    }
}
