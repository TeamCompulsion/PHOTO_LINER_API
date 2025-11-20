package kr.kro.photoliner.domain.album.model;

import java.util.List;

public record Albums(
        List<Album> albums
) {

    public int count() {
        return albums.size();
    }
}
