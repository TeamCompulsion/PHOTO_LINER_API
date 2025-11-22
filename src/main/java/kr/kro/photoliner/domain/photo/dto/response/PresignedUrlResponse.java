package kr.kro.photoliner.domain.photo.dto.response;

public record PresignedUrlResponse(
        String presignedUrl,
        String uploadFileUrl
) {
    public static PresignedUrlResponse of(String presignedUrl, String uploadFileUrl) {
        return new PresignedUrlResponse(presignedUrl, uploadFileUrl);
    }
}