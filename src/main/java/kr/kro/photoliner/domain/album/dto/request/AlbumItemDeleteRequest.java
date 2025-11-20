package kr.kro.photoliner.domain.album.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AlbumItemDeleteRequest(
        @NotNull
        List<Long> ids
) {
}
