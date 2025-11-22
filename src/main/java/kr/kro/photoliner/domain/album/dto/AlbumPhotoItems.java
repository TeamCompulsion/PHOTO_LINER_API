package kr.kro.photoliner.domain.album.dto;

import java.util.List;

public record AlbumPhotoItems(
        List<AlbumPhotoItem> photoItems
) {

    public int count() {
        return photoItems.size();
    }
}
