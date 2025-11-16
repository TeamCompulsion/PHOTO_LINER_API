package kr.kro.photoliner.global.kakao.login.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoOauthTokenResponse(
        String tokenType,
        String accessToken,
        String idToken,
        Integer expiresIn,
        String refreshToken,
        String scope
) {
}
