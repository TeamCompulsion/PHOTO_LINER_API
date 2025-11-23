package kr.kro.photoliner.global.kakao.login.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoTokenRefreshRequest(
        String grantType,
        String clientId,
        String refreshToken,
        String clientSecret
) {
}
