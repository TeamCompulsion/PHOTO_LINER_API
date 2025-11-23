package kr.kro.photoliner.domain.user.service;

import kr.kro.photoliner.domain.user.dto.response.UserInfoResponse;
import kr.kro.photoliner.domain.user.model.User;
import kr.kro.photoliner.domain.user.repository.UserRepository;
import kr.kro.photoliner.global.auth.JwtProvider;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoOauthTokenResponse;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoProfileResponse;
import kr.kro.photoliner.global.kakao.login.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KakaoAuthService kakaoAuthService;
    private final JwtProvider jwtProvider;

    @Value("${client.base-url}")
    public String baseUrl;

    private static final String LOGIN_REDIRECT_URL = "/login/kakao";

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_USER, "user id: " + userId));
        return UserInfoResponse.from(user);
    }

    public String oAuthLogin(String authorizationCode) {
        KakaoOauthTokenResponse tokenResponse = kakaoAuthService.getTokenByAuthorizationCode(authorizationCode);
        String kakaoAccessToken = tokenResponse.accessToken();
        KakaoProfileResponse profileResponse = kakaoAuthService.getKakaoUserProfile(kakaoAccessToken);

        User user = userRepository.findUserByEmail(profileResponse.kakaoAccount().email())
                .orElseGet(() -> signup(User.from(profileResponse)));

        String accessToken = jwtProvider.createAccessToken(user);

        return baseUrl + LOGIN_REDIRECT_URL + "#accessToken=" + accessToken;
    }

    private User signup(User user) {
        return userRepository.save(user);
    }
}
