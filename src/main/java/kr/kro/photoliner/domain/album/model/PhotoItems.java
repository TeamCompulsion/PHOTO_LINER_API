package kr.kro.photoliner.domain.album.model;

import java.util.List;
import kr.kro.photoliner.domain.photo.model.Photo;

public record PhotoItems(
        List<PhotoItem> items
) {

    public static List<PhotoItem> of(List<Long> photoIds) {
        return photoIds.stream()
                .map(id -> new PhotoItem(Photo.builder().id(id).build()))
                .toList();
    }
}
