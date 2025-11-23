package kr.kro.photoliner.global.kakao.login.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoAccessTokenInfoResponse(
        Long id,
        Integer expiresIn,
        Integer appId
) {
}
