package kr.kro.photoliner.domain.user.service;

import kr.kro.photoliner.common.dto.response.JwtResponse;
import kr.kro.photoliner.domain.user.model.User;
import kr.kro.photoliner.domain.user.repository.UserRepository;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoOauthTokenResponse;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoProfileResponse;
import kr.kro.photoliner.global.kakao.login.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KakaoAuthService kakaoAuthService;

    public JwtResponse oAuthLogin(String authorizationCode) {
        KakaoOauthTokenResponse tokenResponse = kakaoAuthService.getTokenByAuthorizationCode(authorizationCode);
        KakaoProfileResponse profileResponse = kakaoAuthService.getKakaoUserProfile(
                tokenResponse.accessToken());

        if (!userRepository.existsByEmail(profileResponse.kakaoAccount().email())) {
            signup(User.from(profileResponse));
        }

        return JwtResponse.from(tokenResponse);
    }

    private void signup(User user) {
        userRepository.save(user);
    }
}
