package kr.kro.photoliner.domain.album.model;

import java.util.List;

public record PhotoItems(
        List<PhotoItem> items
) {

    public static List<PhotoItem> of(List<Long> photoIds) {
        return photoIds.stream()
                .map(id -> PhotoItem.builder().photoId(id).build())
                .toList();
    }
}
