package kr.kro.photoliner.domain.photo.dto.response;

public record PresignedUrlResponse(
        String presignedUrl,
        String uploadFileName
) {

    public static PresignedUrlResponse of(String presignedUrl, String uploadFileName) {
        return new PresignedUrlResponse(presignedUrl, uploadFileName);
    }
}
