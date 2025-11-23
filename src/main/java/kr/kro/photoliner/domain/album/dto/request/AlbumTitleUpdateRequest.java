package kr.kro.photoliner.domain.album.dto.request;

import jakarta.validation.constraints.NotNull;

public record AlbumTitleUpdateRequest(
        @NotNull
        String title
) {
}
