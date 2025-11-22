package kr.kro.photoliner.domain.photo.infra;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import kr.kro.photoliner.domain.photo.dto.request.PresignedUrlRequest;
import kr.kro.photoliner.domain.photo.dto.response.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class S3PresignedUrlGenerator {

    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.cdn.base-url}")
    private String cdnBaseUrl;

    private static final Duration PRESIGNED_URL_EXPIRATION = Duration.ofMinutes(10);

    public List<PresignedUrlResponse> generatePresignedUrls(List<PresignedUrlRequest> requests) {
        return requests.stream()
                .map(this::generatePresignedUrl)
                .toList();
    }

    private PresignedUrlResponse generatePresignedUrl(PresignedUrlRequest request) {
        String objectKey = generateObjectKey(request.originalFileName());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType(request.contentType())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(PRESIGNED_URL_EXPIRATION)
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        String presignedUrl = presignedRequest.url().toString();
        String uploadFileUrl = cdnBaseUrl + "/" + objectKey;

        return PresignedUrlResponse.of(presignedUrl, uploadFileUrl);
    }

    private String generateObjectKey(String originalFileName) {
        String extension = extractExtension(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return "images/original/" + uuid + extension;
    }

    private String extractExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex);
    }
}