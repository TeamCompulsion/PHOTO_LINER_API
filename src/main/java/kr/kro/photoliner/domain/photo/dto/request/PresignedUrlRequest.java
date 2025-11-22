package kr.kro.photoliner.domain.photo.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PresignedUrlRequest(
        @NotBlank(message = "파일명은 필수입니다")
        String originalFileName,

        @NotBlank(message = "Content-Type은 필수입니다")
        String contentType
) {
}