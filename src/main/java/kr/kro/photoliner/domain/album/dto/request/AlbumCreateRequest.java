package kr.kro.photoliner.domain.album.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AlbumCreateRequest(
        @NotNull
        Long userId,
        @NotEmpty
        String title
) {
}
