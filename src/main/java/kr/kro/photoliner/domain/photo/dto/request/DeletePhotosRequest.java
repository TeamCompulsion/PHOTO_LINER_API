package kr.kro.photoliner.domain.photo.dto.request;

import java.util.List;

public record DeletePhotosRequest(
        List<Long> ids
) {

}
