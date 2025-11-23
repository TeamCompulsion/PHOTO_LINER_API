package kr.kro.photoliner.global.auth;

import java.util.Objects;
import kr.kro.photoliner.domain.user.model.User;
import kr.kro.photoliner.domain.user.repository.UserRepository;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Auth authAt = parameter.getParameterAnnotation(Auth.class);
        Objects.requireNonNull(authAt);

        String token = jwtProvider.extractAccessToken(webRequest);
        System.out.println("resolver: " + token);
        jwtProvider.validateToken(token);
        Long userId = jwtProvider.getUserId(token);

        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_USER, "user id: " + userId));

        return user.getId();
    }
}
