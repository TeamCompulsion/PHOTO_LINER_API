package kr.kro.photoliner.domain.photo.model;

import java.util.List;

public record Photos(
        List<Photo> photos
) {

    public int count() {
        return photos.size();
    }

}
