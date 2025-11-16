package kr.kro.photoliner.global.kakao.login.client;

import java.nio.charset.StandardCharsets;
import kr.kro.photoliner.global.kakao.login.constant.KakaoApiUrlConstant;
import kr.kro.photoliner.global.kakao.login.dto.request.KakaoOauthTokenRequest;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoOauthTokenResponse;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoProfileResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoAuthClient {
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public KakaoOauthTokenResponse getOauthToken(KakaoOauthTokenRequest request) {
        return getWebClient(KakaoApiUrlConstant.GET_OAUTH_TOKEN_URL)
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .acceptCharset(StandardCharsets.UTF_8)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(KakaoOauthTokenResponse.class)
                .block();
    }

    public KakaoProfileResponse getKakaoUserProfile(String accessToken) {
        return getWebClient(KakaoApiUrlConstant.GET_USER_PROFILE)
                .get()
                .header("Authorization", BEARER_TOKEN_PREFIX + accessToken)
                .retrieve()
                .bodyToMono(KakaoProfileResponse.class)
                .block();
    }

    private WebClient getWebClient(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
