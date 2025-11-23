package kr.kro.photoliner.domain.album.dto.response;

import kr.kro.photoliner.domain.album.model.Album;

public record AlbumCreateResponse(
        InnerAlbum album
) {

    public static AlbumCreateResponse from(Album album) {
        return new AlbumCreateResponse(
                new InnerAlbum(
                        album.getId(),
                        album.getTitle()
                )
        );
    }

    public record InnerAlbum(
            Long id,
            String name
    ) {

    }
}
