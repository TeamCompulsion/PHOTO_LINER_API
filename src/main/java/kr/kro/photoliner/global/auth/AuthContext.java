package kr.kro.photoliner.global.auth;

import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthContext {

    private Long userId;

    public Long getUserId() {
        if (userId == null) {
            throw CustomException.of(ApiResponseCode.NOT_FOUND_USER, "userId is null");
        }
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
