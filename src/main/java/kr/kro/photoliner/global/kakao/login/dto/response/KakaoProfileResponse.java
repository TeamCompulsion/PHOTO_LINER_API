package kr.kro.photoliner.global.kakao.login.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoProfileResponse(
        Long id,
        InnerKakaoAccount kakaoAccount
) {
    public record InnerKakaoAccount(
            InnerKakaoProfile profile,
            String name,
            Boolean isEmailValid,
            Boolean isEmailVerified,
            String email
    ) {
        public record InnerKakaoProfile(
                String nickname,
                String thumbnailImageUrl,
                String profileImageUrl,
                Boolean isDefaultImage
        ) {

        }
    }
}
