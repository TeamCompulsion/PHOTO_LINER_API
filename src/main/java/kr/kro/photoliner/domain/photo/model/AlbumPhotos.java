package kr.kro.photoliner.domain.photo.model;

import java.util.List;

public record AlbumPhotos(
        List<AlbumPhoto> albumPhotos
) {
    public List<Photo> getPhotoIncludedAlbum(Long albumId) {
        return albumPhotos.stream()
                .filter(albumPhoto -> albumPhoto.isIncludedInAlbum(albumId))
                .map(AlbumPhoto::getPhoto)
                .toList();
    }

    public List<Photo> getPhotoNotIncludedAlbum(Long albumId) {
        return albumPhotos.stream()
                .filter(albumPhoto -> albumPhoto.isIncludedInAlbum(albumId))
                .map(AlbumPhoto::getPhoto)
                .toList();
    }

}
