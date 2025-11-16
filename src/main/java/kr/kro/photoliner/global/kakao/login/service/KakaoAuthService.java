package kr.kro.photoliner.global.kakao.login.service;

import kr.kro.photoliner.global.kakao.login.client.KakaoAuthClient;
import kr.kro.photoliner.global.kakao.login.constant.KakaoApiUrlConstant;
import kr.kro.photoliner.global.kakao.login.dto.request.KakaoOauthTokenRequest;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoOauthTokenResponse;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {
    private final static String DEFAULT_GRANT_TYPE = "authorization_code";
    private final KakaoAuthClient kakaoAuthClient;

    @Value("${kakao.api.rest-api-key}")
    private String restApiKey;
    @Value("${kakao.api.login-oauth.redirect-url}")
    private String redirectUri;

    public String getAuthorizationRedirectUrl() {
        return createAuthorizationRedirectUri(restApiKey, redirectUri);
    }

    public KakaoOauthTokenResponse getTokenByAuthorizationCode(String authorizationCode) {
        return kakaoAuthClient.getOauthToken(
                new KakaoOauthTokenRequest(
                        DEFAULT_GRANT_TYPE,
                        restApiKey,
                        redirectUri,
                        authorizationCode,
                        null
                )
        );
    }

    public KakaoProfileResponse getKakaoUserProfile(String accessToken) {
        return kakaoAuthClient
                .getKakaoUserProfile(accessToken);
    }


    private String createAuthorizationRedirectUri(String restApiKey, String redirectUri) {
        return KakaoApiUrlConstant.AUTHORIZATION_REDIRECT_URL
                + "?response_type=code"
                + "&client_id=" + restApiKey
                + "&redirect_uri=" + redirectUri;
    }
}
