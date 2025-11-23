package kr.kro.photoliner.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;
    private final AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = jwtProvider.extractAccessToken(request);
        if (accessToken == null) {
            return false;
        }
        jwtProvider.validateToken(accessToken);
        Long userId = jwtProvider.getUserId(accessToken);
        authContext.setUserId(userId);
        return true;
    }


}
