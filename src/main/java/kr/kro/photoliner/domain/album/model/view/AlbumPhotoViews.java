package kr.kro.photoliner.domain.album.model.view;

import java.util.List;
import java.util.Objects;

public record AlbumPhotoViews(
        List<AlbumPhotoView> albumPhotoViews
) {

    public List<AlbumPhotoView> filterAlbumIncludedIn(Long albumId) {
        return albumPhotoViews.stream()
                .filter(albumPhotoView -> Objects.equals(albumPhotoView.getAlbumId(), albumId))
                .toList();
    }

    public List<AlbumPhotoView> filterAlbumExcludedFrom(Long albumId) {
        return albumPhotoViews.stream()
                .filter(albumPhotoView -> !Objects.equals(albumPhotoView.getAlbumId(), albumId))
                .toList();
    }
}
