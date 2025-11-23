package kr.kro.photoliner.domain.user.dto.response;

import kr.kro.photoliner.domain.user.model.User;

public record UserInfoResponse(
        String name,
        String email
) {

    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getName(),
                user.getEmail()
        );
    }
}
