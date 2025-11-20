package kr.kro.photoliner.domain.photo.model;

import java.util.List;
import kr.kro.photoliner.domain.album.model.Album;

public record Photos(
        List<Photo> photos
) {

    public int count() {
        return photos.size();
    }

    public List<Photo> filterInAlbum(Album album) {
        return photos.stream()
                .filter(photo -> photo.isIncludedInAlbum(album))
                .toList();
    }

    public List<Photo> filterOutOfAlbum(Album album) {
        return photos.stream()
                .filter(photo -> !photo.isIncludedInAlbum(album))
                .toList();
    }

}
