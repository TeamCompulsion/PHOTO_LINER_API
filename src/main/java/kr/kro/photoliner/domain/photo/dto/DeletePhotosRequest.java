package kr.kro.photoliner.domain.photo.dto;

import java.util.List;

public record DeletePhotosRequest(
        List<Long> ids
) {

}
