package kr.kro.photoliner.domain.user.controller;

import java.net.URI;
import kr.kro.photoliner.common.dto.response.JwtResponse;
import kr.kro.photoliner.domain.user.service.UserService;
import kr.kro.photoliner.global.kakao.login.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final KakaoAuthService kakaoAuthService;
    private static final String LOGIN_REDIRECT_URL = "http://localhost:5173/login/kakao";

    @GetMapping("/login/kakao")
    public ResponseEntity<JwtResponse> login(@RequestParam(value = "code") String authorizationCode) {
        JwtResponse jwtResponse = userService.oAuthLogin(authorizationCode);

        String redirectUrl = LOGIN_REDIRECT_URL + "#accessToken=" + jwtResponse.accessToken() + "&refreshToken="
                + jwtResponse.refreshToken();

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }

    @GetMapping("/login/kakao/authorization")
    public ResponseEntity<Void> authorize() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(kakaoAuthService.getAuthorizationRedirectUrl()))
                .build();
    }
}
