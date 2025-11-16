package kr.kro.photoliner.common.dto.response;

import kr.kro.photoliner.global.kakao.login.dto.response.KakaoOauthTokenResponse;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
    public static JwtResponse from(KakaoOauthTokenResponse oauthTokenResponse) {
        return new JwtResponse(
                oauthTokenResponse.accessToken(),
                oauthTokenResponse.refreshToken()
        );
    }
}
