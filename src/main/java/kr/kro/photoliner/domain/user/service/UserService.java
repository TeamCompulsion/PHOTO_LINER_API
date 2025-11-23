package kr.kro.photoliner.domain.user.service;

import kr.kro.photoliner.common.dto.response.JwtResponse;
import kr.kro.photoliner.domain.user.model.User;
import kr.kro.photoliner.domain.user.repository.UserRepository;
import kr.kro.photoliner.global.auth.JwtProvider;
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
    private final JwtProvider jwtProvider;

    public JwtResponse oAuthLogin(String authorizationCode) {
        KakaoOauthTokenResponse tokenResponse = kakaoAuthService.getTokenByAuthorizationCode(authorizationCode);
        String accessToken = tokenResponse.accessToken();
        KakaoProfileResponse profileResponse = kakaoAuthService.getKakaoUserProfile(accessToken);

        User user = userRepository.findUserByEmail(profileResponse.kakaoAccount().email())
                .orElseGet(() -> signup(User.from(profileResponse)));

        return new JwtResponse(jwtProvider.createAccessToken(user));
    }

    private User signup(User user) {
        return userRepository.save(user);
    }
}
