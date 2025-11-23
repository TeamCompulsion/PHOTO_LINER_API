package kr.kro.photoliner.domain.album.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record AlbumCreateRequest(
        @NotEmpty
        String title
) {
}
