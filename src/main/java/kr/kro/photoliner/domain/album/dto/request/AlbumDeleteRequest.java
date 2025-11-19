package kr.kro.photoliner.domain.album.dto.request;

import java.util.List;

public record AlbumDeleteRequest(
        List<Long> ids
) {
}
