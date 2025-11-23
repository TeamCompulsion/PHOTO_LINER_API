package kr.kro.photoliner.domain.album.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AlbumItemCreateRequest(
        @NotNull
        List<Long> ids
) {
}
