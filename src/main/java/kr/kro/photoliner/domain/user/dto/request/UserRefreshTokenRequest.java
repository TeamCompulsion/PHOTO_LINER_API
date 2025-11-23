package kr.kro.photoliner.domain.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record UserRefreshTokenRequest(
        @NotNull(message = "refresh token 을 입력해주세요.")
        String refreshToken
) {
}
